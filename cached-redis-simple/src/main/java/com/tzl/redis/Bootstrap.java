package com.tzl.redis;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author tan
 * @since 2017-03-05
 * @version 1.0.0.RELEASE
 */
@EnableAutoConfiguration
@ComponentScan("com.tzl")
/*@EnableAspectJAutoProxy(proxyTargetClass=true)*/
@ImportResource({"classpath:config/spring-client.xml"})
@Slf4j
public class Bootstrap{
    // 启动服务
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        log.info("application start success!");
    }
}
