package cn.sx.hu.star.id.zookeeper;

import cn.sx.hu.star.id.core.constant.Algorithm;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.AbstractId;
import cn.sx.hu.star.id.core.factory.IdFactory;
import cn.sx.hu.star.id.core.factory.IdGeneratorFactory;
import cn.sx.hu.star.id.core.generator.impl.IdSnowflakeGenerator;
import cn.sx.hu.star.id.core.specs.Id;
import cn.sx.hu.star.id.core.util.IpUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IdFactory测试
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdFactoryTest {

    private final Logger logger = LoggerFactory.getLogger(IdFactoryTest.class);

    @Before
    public void init() {

        IdFactory.getInstance().id(new AbstractId() {
            @Override
            public long doGetLong(Algorithm algorithm) throws IdException {
                IdGeneratorFactory factory = IdGeneratorFactory.build(IpUtil.getIp(), 8080);
                return factory.getGenerator(IdSnowflakeGenerator.class).generate();
            }
        });

    }

    @Test
    public void testId() throws IdException {
        Id id = IdFactory.getInstance().id();
        Assert.assertNotNull(id);
        long longId = id.getLong();
        logger.info("longId:{}", longId);
        String stringId = id.getString();
        logger.info("stringId:{}", stringId);
        Assert.assertNotEquals(String.valueOf(longId), stringId);
    }

}
