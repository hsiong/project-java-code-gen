/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: ReflectUtil
 * @Author: Hsiong
 * @Date: 2022/11/15 14:45
 * @Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package hsiong.module.codetool.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 〈〉
 *
 * @author Hsiong
 * @create 2022/11/15
 * @since 1.0.0
 */
public class ReflectUtil {

    /**
     * 使用 Function 接口实现获取 类成员变量的名称/路径表达式
     * TIP: https://blog.csdn.net/weixin_44912855/article/details/118543889
     *
     * @param fn
     * @param <T>
     * @return
     */
    protected static <T> String fnToFieldName(TFun<T, ?> fn) {
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String fieldWithGet = serializedLambda.getImplMethodName();
            // 转小驼峰
            char[] chars = fieldWithGet.substring(3).toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将驼峰命名转化成下划线
     *
     * @param para
     * @return
     */
    protected static String camelToUnderline(String para) {
        if (para.length() < 3) {
            return para.toLowerCase();
        }
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        //从第三个字符开始 避免命名不规范
        for (int i = 2; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }
    
    /**
     * 内部类, 用于 Serializable 接口
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    protected interface TFun<T, R> extends Function<T, R>, Serializable {

    }
}
