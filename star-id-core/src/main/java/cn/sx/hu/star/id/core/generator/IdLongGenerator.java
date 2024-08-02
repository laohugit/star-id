package cn.sx.hu.star.id.core.generator;

import cn.sx.hu.star.id.core.specs.IdGenerator;
import cn.sx.hu.star.id.core.exception.IdException;

/**
 * Long类型ID生成器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public abstract class IdLongGenerator implements IdGenerator<Long> {

    @Override
    public Long generate() throws IdException {
        return generateLong();
    }

    public abstract Long generateLong() throws IdException;

}
