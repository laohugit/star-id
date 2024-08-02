package cn.sx.hu.star.id.core;

import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.IdGeneratorFactory;
import cn.sx.hu.star.id.core.generator.impl.IdUUIDGenerator;
import cn.sx.hu.star.id.core.util.IpUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UUIDGenerator测试
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdUUIDGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(IdUUIDGeneratorTest.class);

    /**
     * 测试UUID
     * @throws IdException ID异常
     */
    @Test
    public void testUUIDGenerator() throws IdException {
        IdGeneratorFactory factory = IdGeneratorFactory.build(IpUtil.getIp(), 8080);
        String id = factory.getGenerator(IdUUIDGenerator.class).generate();
        logger.info("{} id:{}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, id);
        Assert.assertNotNull(id);
    }

}
