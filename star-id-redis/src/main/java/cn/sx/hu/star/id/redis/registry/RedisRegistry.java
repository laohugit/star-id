package cn.sx.hu.star.id.redis.registry;

import cn.sx.hu.star.id.core.constant.ErrorMessage;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.model.IdLocation;
import cn.sx.hu.star.id.core.model.IdRegistryContext;
import cn.sx.hu.star.id.core.specs.Registry;

/**
 * Redis注册器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class RedisRegistry implements Registry {

    public void init(IdRegistryContext context) throws IdException {
        throw new IdException(ErrorMessage.UNSUPPORTED);
    }

    public IdLocation register(String ip, int port) throws IdException {
        throw new IdException(ErrorMessage.UNSUPPORTED);
    }

}
