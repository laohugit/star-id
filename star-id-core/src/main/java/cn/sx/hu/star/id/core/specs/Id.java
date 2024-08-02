package cn.sx.hu.star.id.core.specs;

import cn.sx.hu.star.id.core.constant.Algorithm;
import cn.sx.hu.star.id.core.exception.IdException;

/**
 * ID模型
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public interface Id {

    default long getLong() throws IdException {
        return getLong(Algorithm.SNOWFLAKE);
    }

    default String getString() throws IdException {
        return getString(Algorithm.SNOWFLAKE);
    }

    long getLong(Algorithm algorithm) throws IdException;

    String getString(Algorithm algorithm) throws IdException;

}
