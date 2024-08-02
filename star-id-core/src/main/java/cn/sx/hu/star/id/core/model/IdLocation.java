package cn.sx.hu.star.id.core.model;

/**
 * ID定位符
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdLocation {

    public static final IdLocation DEFAULT = new IdLocation(-1, -1);

    private long dataCenterId;
    private long workerId;

    public IdLocation(long dataCenterId, long workerId) {
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "IdLocation{" +
                "workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                '}';
    }

}
