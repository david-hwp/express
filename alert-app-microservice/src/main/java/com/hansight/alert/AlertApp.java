package com.hansight.alert;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

/**
 * @author: huawei_niu@hansight.com
 * @version:
 * @createDate：16/12/23
 * @desc:
 */
@EnableEurekaClient
@EnableOAuth2Client
@RestController
@SpringBootApplication//相当于下面三个同时使用
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@RequestMapping("/dashboard")
public class AlertApp {
    @RequestMapping("/info")
    public String home() {
        return "Hello world";
    }

    @RequestMapping("/message")
    public Map<String, Object> dashboard() {
        return Collections.<String, Object>singletonMap("message", "Hello!");
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AlertApp.class).web(true).run(args);
    }

    @Controller
    public static class LoginErrors {
        @RequestMapping("/dashboard/login")
        public String dashboard() {
            return "redirect:/#/";
        }
    }

    @Component
    @EnableOAuth2Sso
    public static class LoginConfigurer extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/dashboard/**").authorizeRequests().anyRequest()
                    .authenticated().and().csrf()
                    .csrfTokenRepository(csrfTokenRepository()).and()
                    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                    .logout().logoutUrl("/dashboard/logout").permitAll()
                    .logoutSuccessUrl("/");
        }

        private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request
                            .getAttribute(CsrfToken.class.getName());
                    if (csrf != null) {
                        Cookie cookie = new Cookie("XSRF-TOKEN",
                                csrf.getToken());
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }
}
