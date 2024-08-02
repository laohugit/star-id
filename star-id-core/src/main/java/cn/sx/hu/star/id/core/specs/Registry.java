package cn.sx.hu.star.id.core.specs;

import cn.sx.hu.star.id.core.constant.SymbolConstant;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.model.IdLocation;
import cn.sx.hu.star.id.core.model.IdRegistryContext;

import java.util.StringJoiner;

/**
 * 注册器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public interface Registry {

    void init(IdRegistryContext context) throws IdException;

    IdLocation register(String ip, int port) throws IdException;

    default boolean available(IdGenerator generator) {
        return false;
    }

    default void destroy() throws IdException {}

    default String getServiceAddress(String ip, int port) {
        return getServiceAddress(ip, String.valueOf(port));
    }

    default String getServiceAddress(String ip, String port) {
        StringJoiner joiner = new StringJoiner(SymbolConstant.SYMBOL_COLON);
        joiner.add(ip);
        joiner.add(port);
        return joiner.toString();
    }

}
