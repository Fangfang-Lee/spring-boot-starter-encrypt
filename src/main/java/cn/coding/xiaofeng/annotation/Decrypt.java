package cn.coding.xiaofeng.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date 2020/1/8 下午7:38
 * @Email justase@163.com
 * @Author Jason Lee
 * @Description 解密注解，加上该注解的接口将进行解密操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {

}
