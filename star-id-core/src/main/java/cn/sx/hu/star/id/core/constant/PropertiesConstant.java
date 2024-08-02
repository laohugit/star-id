package cn.sx.hu.star.id.core.constant;

/**
 * Properties文件及内容相关常量
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class PropertiesConstant {
    public final static String DEFAULT_FILE_NAME = GlobalConstant.DEFAULT_ID_NAME + ".properties";
    public final static String ENV_FILE_NAME = GlobalConstant.DEFAULT_ID_NAME + "-%s.properties";
    public final static String KEY_REGISTRY_ZOOKEEPER = GlobalConstant.DEFAULT_ID_KEY + ".registry.zookeeper";
    public final static String KEY_REGISTRY_REDIS = GlobalConstant.DEFAULT_ID_KEY + ".registry.redis";
    public final static String KEY_SNOWFLAKE_START_TIME_MILLIS = "snowflake.startTimeMillis";
    public final static String KEY_ZOOKEEPER_ADDRESS = "zookeeper.address";
    public final static String KEY_ENV_VARIABLE_DATA_CENTER = "env.variable.datacenter";
    public final static String KEY_STARTUP_ARG_DATA_CENTER = "startup.arg.datacenter";
}
