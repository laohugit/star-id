package cn.sx.hu.star.id.zookeeper.registry;

import cn.sx.hu.star.id.zookeeper.core.ZookeeperAssistant;
import cn.sx.hu.star.id.zookeeper.hook.Result;
import cn.sx.hu.star.id.zookeeper.model.Registration;
import com.alibaba.fastjson.JSON;
import cn.sx.hu.star.id.core.configuration.Configurer;
import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.model.IdConfiguration;
import cn.sx.hu.star.id.core.model.IdLocation;
import cn.sx.hu.star.id.core.model.IdRegistryContext;
import cn.sx.hu.star.id.core.observer.IdLocationBroadcaster;
import cn.sx.hu.star.id.core.specs.Registry;
import cn.sx.hu.star.id.core.specs.IdGenerator;
import cn.sx.hu.star.id.core.util.EnvUtil;
import cn.sx.hu.star.id.zookeeper.constant.PathConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Zookeeper注册器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class ZookeeperRegistry implements Registry {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private ZookeeperAssistant zkAssistant;
    private IdConfiguration configuration;
    private final Registration registration = new Registration();

    @Override
    public void init(IdRegistryContext idRegistryContext) throws IdException {
        configuration = Configurer.getInstance().getConfiguration();
        zkAssistant = ZookeeperAssistant.getInstance(configuration.getZookeeperAddress());
    }

    @Override
    public IdLocation register(String ip, int port) throws IdException {
        IdLocation idLocation;
        try{
            // 1.注册
            doRegister(ip, port);

            // 2.解析
            idLocation = doAnalyse();

            // 3.监听
            doListen();
        } catch (IdException e) {
            throw e;
        }catch (Exception e) {
            throw new IdException(e);
        }
        return idLocation;
    }

    @Override
    public boolean available(IdGenerator generator) {
        return true;
    }

    private void doRegister(String ip, int port) throws IdException {
        String dataCenter = EnvUtil.getEnvVariable(configuration.getEnvVariable().getDataCenter());
        if(Objects.isNull(dataCenter) || dataCenter.isEmpty()) {
            dataCenter = GlobalConstant.DEFAULT_DATA_CENTER;
        }
        String worker = getServiceAddress(ip, port);

        registration.setIp(ip);
        registration.setPort(port);
        registration.setDataCenter(dataCenter);
        registration.setWorker(worker);
        registration.setCreatedBy(System.currentTimeMillis());
        registration.setUpdatedBy(registration.getCreatedBy());
        registration.setVersion(1);

        zkAssistant.createEphSeqNode(PathConstant.WORKER, JSON.toJSONString(registration));
    }

    private IdLocation doAnalyse() throws IdException {
        IdLocation idLocation = IdLocation.DEFAULT;

        // 1.获取列表
        Map<String,Registration> registrations =  getWorkers();

        // 2.解析datacenterId
        long dataCenterId = analyseDataCenterId(registrations);

        // 3.解析workerId
        long workerId = analyseWorkerId(registrations);

        // 4.更新注册信息
        refreshWorker();

        idLocation.setDataCenterId(dataCenterId);
        idLocation.setWorkerId(workerId);
        return idLocation;
    }

    private void doListen() throws IdException {
        zkAssistant.watchChildren(PathConstant.WORKERS, () -> {
            Result result =  new Result();
            try{
                IdLocationBroadcaster.getInstance().broadcast(doAnalyse());
            }catch (IdException e) {
                result.setThrowable(e);
            }
            return result;
        });
    }

    private Map<String,Registration> getWorkers() throws IdException {
        Map<String,String> childrenData =  zkAssistant.getChildrenNodePathAndData(PathConstant.WORKERS);
        for(String path : childrenData.keySet()) {
            if(logger.isDebugEnabled()){
                logger.debug("{}->{}", path, childrenData.get(path));
            }
        }

        // 数据转换
        Map<String,Registration> registrations = new TreeMap<>();
        for(String key : childrenData.keySet()) {
            Registration value = JSON.parseObject(childrenData.get(key), Registration.class);
            value.setPath(PathConstant.ROOT + "/" + key);
            registrations.put(key, value);
        }
        for(String path : registrations.keySet()) {
            if(logger.isDebugEnabled()){
                logger.debug("{}->{}", path, registrations.get(path));
            }
        }
        return registrations;
    }

    public long analyseDataCenterId(Map<String,Registration> registrations) {
        Set<String> dataCenterSet = new HashSet<>();
        List<String> dataCenterList = new ArrayList<>();
        for(String path : registrations.keySet()) {
            Registration reg = registrations.get(path);
            if(reg == null || dataCenterSet.contains(reg.getDataCenter())) {
                continue;
            }
            dataCenterSet.add(reg.getDataCenter());
            dataCenterList.add(reg.getDataCenter());
            if(registration.getDataCenter().equals(reg.getDataCenter())) {
                break;
            }
        }

        long dataCenterId = dataCenterList.size() - 1;
        if(logger.isDebugEnabled()){
            logger.debug("dataCenterId: {}", dataCenterId);
        }

        registration.setDataCenterId(dataCenterId);
        return dataCenterId;
    }

    public long analyseWorkerId(Map<String,Registration> registrations) {
        List<String> workerList = new ArrayList<>();
        for(String path : registrations.keySet()) {
            Registration reg = registrations.get(path);
            if(Objects.isNull(reg)) {
                continue;
            }
            if(!registration.getDataCenter().equals(reg.getDataCenter())) {
                continue;
            }
            workerList.add(reg.getWorker());
            if(registration.getWorker().equals(reg.getWorker())) {
                registration.setPath(PathConstant.WORKERS + "/" + path);
                break;
            }
        }

        int workerId = workerList.size() - 1;
        if(logger.isDebugEnabled()){
            logger.debug("workerId: {}", workerId);
        }

        registration.setWorkerId(workerId);
        return workerId;
    }

    private void refreshWorker() throws IdException {
        registration.setVersion(registration.getVersion() + 1);
        registration.setUpdatedBy(System.currentTimeMillis());
        if(logger.isDebugEnabled()){
            logger.debug("refreshWorker: {}", registration);
        }
        zkAssistant.updateNode(registration.getPath(), JSON.toJSONString(registration));
    }

}
