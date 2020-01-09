package com.duliday.encrypt.advice;

import com.duliday.encrypt.annotation.Decrypt;
import com.duliday.encrypt.config.EncryptProperties;
import com.duliday.encrypt.util.AESUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

/**
 * @Date 2020/1/8 下午7:38
 * @Email justase@163.com
 * @Author Jason Lee
 * @Description 解密注解，加上该注解的接口将进行解密操作
 */
@ControllerAdvice
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(EncryptRequestBodyAdvice.class);

    @Autowired
    private EncryptProperties properties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        boolean decrypt = false;
        if (parameter.getMethod().isAnnotationPresent(Decrypt.class) && !properties.isDebug()) {
            decrypt = true;
        }
        if (decrypt) {
            try {
                return new DecryptHttpInputMessage(inputMessage, properties.getKey(), properties.getCharset());
            } catch (Exception e) {
                logger.error("decrypt data error, e : {}", e);
            }

        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
class DecryptHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;

    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String key, String charset) throws Exception {
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), charset);
        // JSON类型的跳过
        String decryptBody;
        if (content.startsWith("{")) {
            decryptBody = content;
        } else {
            decryptBody = AESUtils.decrypt(content, key);
        }
        this.body = IOUtils.toInputStream(decryptBody, charset);
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}