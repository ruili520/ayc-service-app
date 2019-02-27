
package com.ayc.service.app.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * app项目常用配置
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 14:40
 */
@Configuration
@ConfigurationProperties(prefix = "ayc.app")
public class AppGlobalConfigs {

    /**
     * 项目地址
     */
    private String url;
    /**
     * 主机地址
     */
    private String host;

    /**
     * 项目端口
     */
    private String port;

    /**
     * web服务器地址
     */
    private String webUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}