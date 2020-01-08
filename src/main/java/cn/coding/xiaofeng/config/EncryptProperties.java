package cn.coding.xiaofeng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    private String key;

    private String charset = "UTF-8";

    /**
     * 开启调试模式，调试模式下不进行加解密操作
     */
    private boolean debug = false;

    private Long signExpireTime = 10L;

    public void setKey(String key) {
        this.key = key;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setSignExpireTime(Long signExpireTime) {
        this.signExpireTime = signExpireTime;
    }

    public String getKey() {

        return key;
    }

    public String getCharset() {
        return charset;
    }

    public boolean isDebug() {
        return debug;
    }

    public Long getSignExpireTime() {
        return signExpireTime;
    }
}
