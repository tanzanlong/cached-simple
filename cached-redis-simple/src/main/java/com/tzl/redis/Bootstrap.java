package com.baibei.portal.special;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author hkw
 * @since 2017-03-05
 * @version 1.0.0.RELEASE
 */
@SpringBootApplication
@ImportResource({"classpath:spring/spring-config.xml",
        "classpath:spring/spring-client.xml",
        "classpath:spring/spring-dubbo-consumer.xml",
        "classpath:spring/spring-dubbo-provider.xml",
        "classpath:spring/spring-data.xml",
        "classpath:spring/spring-session-redis.xml",//使用spring session 替换掉HttpSession 实现单点登录session 共享
})
@ComponentScan("com.baibei.portal")
@Slf4j
public class Bootstrap{
    // 启动服务
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        log.info("application start success!");
    }
}
