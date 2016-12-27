package com.hansight.alert;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huawei_niu@hansight.com
 * @version:
 * @createDate：16/12/23
 * @desc:
 */
@Configuration
@EnableEurekaClient
@EnableAutoConfiguration
@EnableOAuth2Client
@ComponentScan
@RestController
public class AlertApp {
    @RequestMapping("/info")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AlertApp.class).web(true).run(args);
    }
}
