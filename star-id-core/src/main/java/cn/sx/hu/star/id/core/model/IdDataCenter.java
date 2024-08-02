package cn.sx.hu.star.id.core.model;

import java.util.List;

/**
 * ID数据中心
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdDataCenter {

    private String code;
    private String group;
    private List<IdWorker> idWorkers;

    public IdDataCenter() {}

    public IdDataCenter(String code) {
        this.code = code;
    }

    public IdDataCenter(String code, String group) {
        this.code = code;
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<IdWorker> getWorkers() {
        return idWorkers;
    }

    public void setWorkers(List<IdWorker> idWorkers) {
        this.idWorkers = idWorkers;
    }

    @Override
    public String toString() {
        return "IdDataCenter{" +
                "code='" + code + '\'' +
                ", group='" + group + '\'' +
                ", workers=" + idWorkers +
                '}';
    }

}
