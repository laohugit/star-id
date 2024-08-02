package cn.sx.hu.star.id.core.model;

/**
 * ID配置信息
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdConfiguration {

    private Long snowflakeStartTimeMillis;
    private String zookeeperAddress;
    private IdEnvVariable envVariable;
    private IdStartupArg startupArg;
    private String redisAddress;

    public Long getSnowflakeStartTimeMillis() {
        return snowflakeStartTimeMillis;
    }

    public void setSnowflakeStartTimeMillis(Long snowflakeStartTimeMillis) {
        this.snowflakeStartTimeMillis = snowflakeStartTimeMillis;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }

    public void setZookeeperAddress(String zookeeperAddress) {
        this.zookeeperAddress = zookeeperAddress;
    }

    public IdEnvVariable getEnvVariable() {
        return envVariable;
    }

    public void setEnvVariable(IdEnvVariable envVariable) {
        this.envVariable = envVariable;
    }

    public IdStartupArg getStartupArg() {
        return startupArg;
    }

    public void setStartupArg(IdStartupArg startupArg) {
        this.startupArg = startupArg;
    }

    public String getRedisAddress() {
        return redisAddress;
    }

    public void setRedisAddress(String redisAddress) {
        this.redisAddress = redisAddress;
    }

}
