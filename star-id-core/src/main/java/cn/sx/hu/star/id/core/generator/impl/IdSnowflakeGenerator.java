package cn.sx.hu.star.id.core.generator.impl;

import cn.sx.hu.star.id.core.configuration.Configurer;
import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.generator.IdLongGenerator;
import cn.sx.hu.star.id.core.model.IdConfiguration;
import cn.sx.hu.star.id.core.model.IdGeneratorInitParam;
import cn.sx.hu.star.id.core.model.IdLocation;
import cn.sx.hu.star.id.core.model.IdRegistryContext;
import cn.sx.hu.star.id.core.observer.IdLocationBroadcaster;
import cn.sx.hu.star.id.core.observer.IdLocationListener;
import cn.sx.hu.star.id.core.specs.Registry;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.algorithm.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Service;

import java.util.Iterator;
import java.util.Objects;

public class IdSnowflakeGenerator extends IdLongGenerator implements IdLocationListener {

    private final Logger logger = LoggerFactory.getLogger(IdSnowflakeGenerator.class);

    private IdLocation idLocation;
    private Snowflake snowflake;
    private Registry registry;
    private IdConfiguration idConfiguration;

    @Override
    public void init(IdGeneratorInitParam param) throws IdException {
        IdRegistryContext context = new IdRegistryContext();
        context.setIp(param.getIp());
        context.setPort(param.getPort());
        context.setEnv(param.getEnv());
        Iterator<Registry> registries = Service.providers(Registry.class);
        while(registries.hasNext()) {
            registry = registries.next();
            if(Objects.nonNull(registry) && registry.available(this)) {
                registry.init(context);
                this.idLocation = registry.register(context.getIp(), context.getPort());
                doCheck(this.idLocation);
                break;
            }
        }
        idConfiguration = Configurer.getInstance().getConfiguration();
        IdLocationBroadcaster.getInstance().register(this);
    }

    @Override
    public Long generateLong() throws IdException {
        if(Objects.nonNull(snowflake)) {
            logger.info("{} generateLong-location: dataCenterId={}, workerId={}",
                    GlobalConstant.DEFAULT_ID_DISPLAY_NAME, idLocation.getDataCenterId(), idLocation.getWorkerId());
            return snowflake.nextId();
        }
        if(Objects.isNull(this.idLocation)) {
            throw new IdException("location is null, register failed!");
        }
        Long startTimeMillis = idConfiguration.getSnowflakeStartTimeMillis();
        if(Objects.isNull(startTimeMillis)) {
            snowflake = new Snowflake(this.idLocation.getDataCenterId(), this.idLocation.getWorkerId());
        }else{
            logger.info("{} startTimeMillis:{}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, startTimeMillis);
            snowflake = new Snowflake(this.idLocation.getDataCenterId(), this.idLocation.getWorkerId(), startTimeMillis);
        }
        return generateLong();
    }

    @Override
    public void onEvent(IdLocation idLocation) {
        if(logger.isDebugEnabled()) {
            logger.debug("{} onEvent: {}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, idLocation);
        }
        this.idLocation = idLocation;
    }

    private void doCheck(IdLocation idLocation) throws IdException {
        if(Objects.isNull(idLocation)) {
            throw new IdException("location is null, register failed!");
        }
        if(idLocation.getDataCenterId() < 0 || idLocation.getDataCenterId() > 31) {
            throw new IdException("invalid data center id, register failed!");
        }
        if(idLocation.getWorkerId() < 0 || idLocation.getWorkerId() > 31) {
            throw new IdException("invalid worker id, register failed!");
        }
    }

    @Override
    public void destroy() throws IdException {
        if(Objects.nonNull(registry)) {
            registry.destroy();
        }
    }

}
