package cn.sx.hu.star.id.core.generator;

import cn.sx.hu.star.id.core.specs.IdGenerator;
import cn.sx.hu.star.id.core.exception.IdException;

/**
 * 字符串类型ID生成器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public abstract class IdStringGenerator implements IdGenerator<String> {

    @Override
    public String generate() throws IdException {
        return generateString();
    }

    public abstract String generateString() throws IdException;

}
