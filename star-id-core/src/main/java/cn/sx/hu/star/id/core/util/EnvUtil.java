package cn.sx.hu.star.id.core.util;

import java.util.Map;

/**
 * 环境信息工具类
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class EnvUtil {

    public static String getEnvVariable(String name) {
        Map<String, String> envs = System.getenv();
        for(String key : envs.keySet()) {
           if(key.equals(name)) {
               return envs.get(key);
           }
        }
        return null;
    }

}
