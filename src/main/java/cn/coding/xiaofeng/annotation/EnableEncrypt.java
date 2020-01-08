package cn.coding.xiaofeng.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;


/**
 * @Date 2020/1/8 下午7:38
 * @Email justase@163.com
 * @Author Jason Lee
 * @Description 启用加密Starter，在启动类上加上该注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableAutoConfiguration.class})
public @interface EnableEncrypt {

}
