package cn.sx.hu.star.id.core.factory;

import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.specs.Id;

/**
 * ID工厂
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdFactory {

    private static final IdFactory instance = new IdFactory();
    private volatile Id id;

    private IdFactory(){}

    public static IdFactory getInstance(){
        return instance;
    }

    public Id id() throws IdException {
        if(id == null) {
            throw new IdException();
        }
        return id;
    }

    public void id(Id id) {
        if(this.id != null) {
            return;
        }
        synchronized (IdFactory.class){
            if(this.id != null) {
                return;
            }
            this.id = id;
        }
    }

}
