package cn.coding.xiaofeng.advice;

import cn.coding.xiaofeng.annotation.Encrypt;
import cn.coding.xiaofeng.config.EncryptProperties;
import cn.coding.xiaofeng.util.AESUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Date 2020/1/8 下午7:38
 * @Email justase@163.com
 * @Author Jason Lee
 * @Description 对加了@Encrypt的方法进行加密操作
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EncryptProperties properties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        boolean encrypt = false;
        if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && !properties.isDebug()) {
            encrypt = true;
        }
        if (encrypt) {
            try {
                String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                if (!StringUtils.hasText(properties.getKey())) {
                    throw new RuntimeException("please configure spring.encrypt.key");
                }
                return AESUtils.encrypt(content, properties.getKey());
            } catch (Exception e) {
                logger.error("encrypt data error, e : {}", e);
            }
        }
        return body;
    }
}
