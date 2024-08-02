package cn.sx.hu.star.id.core.observer;

import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.model.IdLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ID定位符广播器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdLocationBroadcaster {

    private final Logger logger = LoggerFactory.getLogger(IdLocationBroadcaster.class);

    private final List<IdLocationListener> listeners = new ArrayList<>();

    private static final IdLocationBroadcaster instance = new IdLocationBroadcaster();

    private IdLocationBroadcaster(){}

    public static IdLocationBroadcaster getInstance() {
        return instance;
    }

    public void broadcast(IdLocation idLocation){
        logger.info("{} broadcast: {}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, idLocation);
        if(idLocation == null) {
            return;
        }
        listeners.forEach(listener -> listener.onEvent(idLocation));
    }

    public void register(IdLocationListener listener) {
        if(listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

}
