package service.base;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by l_yy on 2017/1/6.
 */

@EnableAutoConfiguration
@ComponentScan("com.weipan.tradeservice")
@ImportResource({"classpath:config/trade-spring-context-test-profile.xml" })
public class TestBootstrap {
/*    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestBootstrap.class);
*//*        StandardEnvironment standardEnvironment = new StandardEnvironment();
        standardEnvironment.getSystemEnvironment().put("spring.cloud.config.uri", "http://127.0.0.1.3:9010");
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.CONSOLE)
                .sources(Parent.class)
                .child(TestBootstrap.class)
                .environment(standardEnvironment)
                .run(args);*//*
        // 启动Reporter
        // Slf4jReporter reporter = ctx.getBean(Slf4jReporter.class);
        // reporter.start(3, TimeUnit.SECONDS);
    }*/


}
