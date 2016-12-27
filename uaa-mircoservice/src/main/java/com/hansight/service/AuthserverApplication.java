package com.hansight.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author: huawei_niu@hansight.com
 * @version:
 * @createDate：16/12/23
 * @desc:
 */
@SpringBootApplication
@EnableSidecar
@EnableEurekaClient
@EnableAuthorizationServer
public class AuthserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory() // 使用in-memory存储
                    .withClient("client") // client_id
                    .secret("secret") // client_secret
                    .authorizedGrantTypes("authorization_code") // 该client允许的授权类型
                    .scopes("app"); // 允许的授权范围
        }
    }

}