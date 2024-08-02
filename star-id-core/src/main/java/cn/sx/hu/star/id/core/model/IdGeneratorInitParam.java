package cn.sx.hu.star.id.core.model;

import cn.sx.hu.star.id.core.constant.Env;

/**
 * ID生成器初始化参数
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdGeneratorInitParam {

    private String ip;
    private int port;
    private Env env;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Env getEnv() {
        return env;
    }

    public static class Builder {

        private String ip;
        private int port;
        private Env env;

        public String ip() {
            return ip;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public int port() {
            return port;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Env env() {
            return env;
        }

        public Builder env(Env env) {
            this.env = env;
            return this;
        }

        public IdGeneratorInitParam build(){
            IdGeneratorInitParam param = new IdGeneratorInitParam();
            param.ip = this.ip();
            param.port = this.port();
            param.env = this.env();
            return param;
        }

    }

}
