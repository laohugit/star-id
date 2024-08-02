package cn.sx.hu.star.id.core.model;

import java.util.List;

/**
 * ID应用进程信息
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 *
 * @author hu
 */
public class IdWorker {

    private List<String> ips;
    private int port;
    private List<String> serviceAddresses;

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getServiceAddresses() {
        return serviceAddresses;
    }

    public void setServiceAddresses(List<String> serviceAddresses) {
        this.serviceAddresses = serviceAddresses;
    }

    @Override
    public String toString() {
        return "IdWorker{" +
                "ips=" + ips +
                ", port='" + port + '\'' +
                ", serviceAddresses=" + serviceAddresses +
                '}';
    }

}
