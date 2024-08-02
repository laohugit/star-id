package cn.sx.hu.star.id.core.constant;

/**
 * 环境标识
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public enum Env {

    LOCAL("local"),
    DEV("dev"),
    TEST("test"),
    STG("stg"),
    SIT("sit"),
    PRD("prd"),
    NONE("none");

    private String code;

    Env(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
