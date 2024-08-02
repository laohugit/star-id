package cn.sx.hu.star.id.springboot.config;

import cn.sx.hu.star.id.core.constant.Algorithm;
import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.AbstractId;
import cn.sx.hu.star.id.core.factory.IdFactory;
import cn.sx.hu.star.id.core.factory.IdGeneratorFactory;
import cn.sx.hu.star.id.core.generator.impl.IdSnowflakeGenerator;
import cn.sx.hu.star.id.core.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot自动配置
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
@Configuration
public class IdAutoConfiguration implements ApplicationListener<WebServerInitializedEvent>  {

    private static final Logger logger = LoggerFactory.getLogger(IdAutoConfiguration.class);

    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            final IdGeneratorFactory factory
                    = IdGeneratorFactory.build(IpUtil.getIp(), event.getWebServer().getPort());
            if(logger.isDebugEnabled()) {
                logger.debug("{} auto configure, ip:{}, port:{}",
                        GlobalConstant.DEFAULT_ID_DISPLAY_NAME, IpUtil.getIp(), event.getWebServer().getPort());
            }
            IdFactory.getInstance().id(new AbstractId() {
                @Override
                public long doGetLong(Algorithm algorithm) throws IdException {
                    return factory.getGenerator(IdSnowflakeGenerator.class).generateLong();
                }
            });
            IdFactory.getInstance().id().getLong();
        } catch (IdException e) {
            logger.error(GlobalConstant.DEFAULT_ID_DISPLAY_NAME + " auto configure exception: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
