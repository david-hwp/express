package com.hansight.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huawei_niu@hansight.com
 * @version:
 * @createDate：16/12/23
 * @desc:
 */
@Configuration
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerApplication {

        public static void main(String[] args) {
            SpringApplication.run(ConfigServerApplication.class, args);
        }

}
