package cn.sx.hu.star.id.core.exception;

/**
 * ID异常
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class IdException extends Exception {

    public IdException(){
        super();
    }

    public IdException(Throwable cause){
        super(cause);
    }

    public IdException(String message){
        super(message);
    }

    public IdException(String message, Throwable cause){
        super(message, cause);
    }

}
