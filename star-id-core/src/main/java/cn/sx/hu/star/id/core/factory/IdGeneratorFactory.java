package cn.sx.hu.star.id.core.factory;

import cn.sx.hu.star.id.core.constant.Env;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.model.IdGeneratorInitParam;
import cn.sx.hu.star.id.core.specs.IdGenerator;
import sun.misc.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * ID生成器工厂
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
@SuppressWarnings("unchecked,rawtypes")
public class IdGeneratorFactory {

    private final Map<Class,Object> cache = new HashMap<>();

    private IdGeneratorFactory(){}

    private IdGeneratorInitParam param;

    public <T extends IdGenerator> T getGenerator(Class<T> clazz) throws IdException {
        if(Objects.nonNull(cache.get(clazz))) {
            return (T)cache.get(clazz);
        }
        Iterator<IdGenerator> generators = Service.providers(IdGenerator.class);
        while(generators.hasNext()) {
            IdGenerator generator = generators.next();
            if(generator.getClass().isAssignableFrom(clazz)) {
                generator.init(param);
                cache.put(clazz, generator);
                return (T)generator;
            }
        }
        return null;
    }

    public static IdGeneratorFactory build(String ip, int port) throws IdException {
        return build(ip, port, Env.NONE);
    }

    public static IdGeneratorFactory build(String ip, int port, Env env) throws IdException {
        IdGeneratorFactory factory = new IdGeneratorFactory();
        factory.setParam(new IdGeneratorInitParam.Builder()
                .ip(ip)
                .port(port)
                .env(env)
                .build());
        return factory;
    }

    public IdGeneratorInitParam getParam() {
        return param;
    }

    public void setParam(IdGeneratorInitParam param) {
        this.param = param;
    }

}
