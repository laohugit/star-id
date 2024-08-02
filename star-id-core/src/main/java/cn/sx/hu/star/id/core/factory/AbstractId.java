package cn.sx.hu.star.id.core.factory;

import cn.sx.hu.star.id.core.constant.Algorithm;
import cn.sx.hu.star.id.core.constant.ErrorMessage;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.specs.Id;

import java.util.UUID;

/**
 * 抽象ID
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public abstract class AbstractId implements Id {

    @Override
    public long getLong(Algorithm algorithm) throws IdException {
        if(algorithm == Algorithm.UUID) {
            throw new IdException(ErrorMessage.INVALID_ALGORITHM);
        }
        if(algorithm == Algorithm.TIMESTAMP) {
            return System.currentTimeMillis();
        }
        return doGetLong(algorithm);
    }

    @Override
    public String getString(Algorithm algorithm) throws IdException {
        if(algorithm == Algorithm.UUID) {
            return UUID.randomUUID().toString();
        }
        return String.valueOf(getLong(algorithm));
    }

    public abstract long doGetLong(Algorithm algorithm) throws IdException;

}
