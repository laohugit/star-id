package cn.sx.hu.star.id.xml;

import cn.sx.hu.star.id.core.constant.Env;
import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.IdGeneratorFactory;
import cn.sx.hu.star.id.core.generator.impl.IdSnowflakeGenerator;
import cn.sx.hu.star.id.core.util.IpUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式雪花算法测试
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdSnowflakeGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(IdSnowflakeGeneratorTest.class);

    /**
     * 默认环境信息场景
     * @throws IdException ID异常
     */
    @Test
    public void testId4Snowflake() throws IdException {
        logger.info("IdGeneratorFactory,ip:{}", IpUtil.getIp());
        IdGeneratorFactory factory = IdGeneratorFactory.build(TestConstant.INSTANCE_IP, TestConstant.INSTANCE_PORT);
        Long id = factory.getGenerator(IdSnowflakeGenerator.class).generate();
        logger.info("{} id:{}",GlobalConstant.DEFAULT_ID_DISPLAY_NAME, id);
    }

    /**
     * 指定环境信息场景
     * @throws IdException ID异常
     */
    @Test
    public void testId4SnowflakeWithEnv() throws IdException {
        IdGeneratorFactory factory = IdGeneratorFactory.build(TestConstant.INSTANCE_IP, TestConstant.INSTANCE_PORT, Env.STG);
        Long id = factory.getGenerator(IdSnowflakeGenerator.class).generate();
        logger.info("{} id:{}",GlobalConstant.DEFAULT_ID_DISPLAY_NAME, id);
    }

}
