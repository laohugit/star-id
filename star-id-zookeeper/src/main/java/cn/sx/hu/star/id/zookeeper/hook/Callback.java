package cn.sx.hu.star.id.zookeeper.hook;

/**
 * 回调接口
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public interface Callback<T> {

    T invoke();

}
