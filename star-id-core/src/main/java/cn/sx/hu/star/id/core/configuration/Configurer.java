package cn.sx.hu.star.id.core.configuration;

import cn.sx.hu.star.id.core.io.ResourceLoader;
import cn.sx.hu.star.id.core.model.IdConfiguration;
import cn.sx.hu.star.id.core.model.IdEnvVariable;
import cn.sx.hu.star.id.core.model.IdStartupArg;
import cn.sx.hu.star.id.core.constant.Env;
import cn.sx.hu.star.id.core.constant.PropertiesConstant;
import cn.sx.hu.star.id.core.exception.IdException;

import java.util.Properties;

/**
 * 配置器
 *
 * <p>加载配置文件
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @see PropertiesConstant
 * @author hu
 */
public class Configurer {

    private IdConfiguration cfg;

    private static final Configurer instance = new Configurer();

    private Configurer(){
    }

    public static Configurer getInstance() {
        return instance;
    }

    public synchronized IdConfiguration getConfiguration(Env env) throws IdException {
        if(cfg != null) {
            return cfg;
        }
        try{
            cfg = new IdConfiguration();
            Properties properties = ResourceLoader.getResourceAsProperties(PropertiesConstant.DEFAULT_FILE_NAME);
            String startTimeMillis = properties.getProperty(PropertiesConstant.KEY_SNOWFLAKE_START_TIME_MILLIS);
            cfg.setSnowflakeStartTimeMillis(Long.parseLong(startTimeMillis));
            cfg.setZookeeperAddress(properties.getProperty(PropertiesConstant.KEY_ZOOKEEPER_ADDRESS));
            IdEnvVariable envVariable = new IdEnvVariable();
            envVariable.setDataCenter(properties.getProperty(PropertiesConstant.KEY_ENV_VARIABLE_DATA_CENTER));
            cfg.setEnvVariable(envVariable);
            IdStartupArg startupArg = new IdStartupArg();
            startupArg.setDataCenter(properties.getProperty(PropertiesConstant.KEY_STARTUP_ARG_DATA_CENTER));
            cfg.setStartupArg(startupArg);
        }catch (Exception e) {
            throw new IdException(e);
        }
        return cfg;
    }

    public IdConfiguration getConfiguration() throws IdException {
        return getConfiguration(null);
    }

}
