package cn.sx.hu.star.id.zookeeper.constant;

import cn.sx.hu.star.id.core.constant.GlobalConstant;

/**
 * Zookeeper路径常量
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class PathConstant {

    public static final String ROOT = "/" + GlobalConstant.DEFAULT_ID_NAME;
    public static final String WORKERS = ROOT + "/workers";
    public static final String WORKER = WORKERS + "/worker";

}
