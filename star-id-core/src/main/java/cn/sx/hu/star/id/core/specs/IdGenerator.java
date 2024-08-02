package cn.sx.hu.star.id.core.specs;

import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.model.IdGeneratorInitParam;

/**
 * ID生成器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public interface IdGenerator<T> {

    /**
     * 初始化
     * @param param 初始化参数
     * @throws IdException ID异常
     */
    default void init(IdGeneratorInitParam param) throws IdException {
    }

    /**
     * 生成ID
     * @return ID
     * @throws IdException ID异常
     */
    T generate() throws IdException;

    /**
     * 销毁
     * @throws IdException ID异常
     */
    default void destroy() throws IdException {
    }

}
