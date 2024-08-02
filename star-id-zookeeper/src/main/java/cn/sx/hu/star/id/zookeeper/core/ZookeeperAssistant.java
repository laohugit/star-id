package cn.sx.hu.star.id.zookeeper.core;

import cn.sx.hu.star.id.zookeeper.hook.Result;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.zookeeper.hook.Callback;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Zookeeper助手
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class ZookeeperAssistant {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperAssistant.class);

    private final CuratorFramework curator;
    private static volatile ZookeeperAssistant instance;

    private ZookeeperAssistant(String address) throws IdException {
        try{
            curator = CuratorFrameworkFactory.builder()
                    .connectString(address)
                    .retryPolicy(new RetryUntilElapsed(1000, 4))
                    .connectionTimeoutMs(6000)
                    .sessionTimeoutMs(10000)
                    .build();
            curator.start();
        }catch (Exception e) {
            throw new IdException(e);
        }
    }

    public void stop() throws IdException {
        if(Objects.isNull(curator)) {
            return;
        }
        curator.close();
    }

    public static ZookeeperAssistant getInstance(String address) throws IdException {
        if(instance == null) {
            synchronized (ZookeeperAssistant.class) {
                if(instance == null) {
                    instance = new ZookeeperAssistant(address);
                }
            }
        }
        return instance;
    }

    public void createNode(String path, String data) throws IdException {
        try {
            curator.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.PERSISTENT)
                   .forPath(path, data.getBytes());
        }catch (Exception e) {
            throw new IdException(e);
        }
    }

    public void createEphSeqNode(String path, String data) throws IdException {
        try {
            curator.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path, data.getBytes());
        }catch (Exception e) {
            throw new IdException(e);
        }
    }

    public String getNodeData(String path) throws Exception {
        byte[] data = curator.getData().storingStatIn(new Stat()).forPath(path);
        return new String(data);
    }

    public List<String> getChildrenNodeData(String path) throws Exception {
        List<String> childrenPath = getChildrenNodePath(path);
        List<String> childrenData = new ArrayList<>();
        for(String childPath : childrenPath) {
            String childData = getNodeData(path + "/" + childPath);
            if(childData != null) {
                childrenData.add(childData);
            }
        }
        return childrenData;
    }

    public Map<String,String> getChildrenNodePathAndData(String path) throws IdException {
        Map<String,String> childrenData = new HashMap<>();
        try {
            List<String> childrenPath = getChildrenNodePath(path);
            for(String childPath : childrenPath) {
                String childData = getNodeData(path + "/" + childPath);
                if(childData != null) {
                    childrenData.put(childPath, childData);
                }
            }
        }catch (Exception e) {
            throw new IdException(e);
        }
        return childrenData;
    }

    public List<String> getChildrenNodePath(String path) throws Exception {
        return curator.getChildren().forPath(path);
    }

    public boolean exists(String path) throws Exception {
        Stat stat = curator.checkExists().forPath(path);
        return null != stat;
    }

    public void updateNode(String path, String data) throws IdException {
        try{
            curator.setData().withVersion(-1).forPath(path, data.getBytes());
        }catch (Exception e) {
            throw new IdException(e);
        }
    }

    public void deleteNode(String path) throws Exception {
        if(!exists(path)) {
            return;
        }
        curator.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
    }

    public void watchChildren(String path, Callback<Result> callback) throws IdException {
        try{
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curator, path, true);
            pathChildrenCache.getListenable().addListener((curator, event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                    case CHILD_REMOVED: {
                        if(logger.isDebugEnabled()){
                            logger.debug("watchChildren-eventType:{}, eventData:{}", event.getType(), event.getData());
                        }
                        Result result = callback.invoke();
                        if(Objects.nonNull(result) && Objects.nonNull(result.getThrowable())) {
                            throw new Exception(result.getThrowable());
                        }
                    }
                    case CHILD_UPDATED:
                    default:
                        break;
                }
            });
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            throw new IdException(e);
        }
    }

}
