package cn.sx.hu.star.id.core.observer;

import cn.sx.hu.star.id.core.model.IdLocation;

/**
 * ID定位符监听器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public interface IdLocationListener {

    void onEvent(IdLocation idLocation);

}
