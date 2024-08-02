package cn.sx.hu.star.id.core.model;

import cn.sx.hu.star.id.core.constant.Env;

/**
 * ID注册器上下文
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdRegistryContext {

    private String ip;
    private int port;
    private Env env;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

}
