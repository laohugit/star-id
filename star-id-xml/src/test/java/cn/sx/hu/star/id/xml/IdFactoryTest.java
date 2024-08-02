package cn.sx.hu.star.id.xml;

import cn.sx.hu.star.id.core.constant.Algorithm;
import cn.sx.hu.star.id.core.constant.Env;
import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.AbstractId;
import cn.sx.hu.star.id.core.factory.IdFactory;
import cn.sx.hu.star.id.core.factory.IdGeneratorFactory;
import cn.sx.hu.star.id.core.generator.impl.IdSnowflakeGenerator;
import cn.sx.hu.star.id.core.specs.Id;
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
                IdGeneratorFactory factory = IdGeneratorFactory.build(TestConstant.INSTANCE_IP, TestConstant.INSTANCE_PORT, Env.STG);
                return factory.getGenerator(IdSnowflakeGenerator.class).generate();
            }
        });

    }

    @Test
    public void testId() throws IdException {
        Id id = IdFactory.getInstance().id();
        Assert.assertNotNull(id);
        logger.info("{} Long:{}, String:{}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, id.getLong(), id.getString(Algorithm.TIMESTAMP));
    }

}
