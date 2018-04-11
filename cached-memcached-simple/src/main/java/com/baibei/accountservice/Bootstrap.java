package com.baibei.accountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.baibei.log4k8s.Log4k8s;
import com.restMvcFilter.MvcTraceFilter;

/**
 * Created by l_yy on 2017/3/9. clean deploy -Dmaven.test.skip=true
 */
@EnableAutoConfiguration
@ComponentScan("com.baibei.accountservice")
/*@EnableAspectJAutoProxy(proxyTargetClass=true)*/
@ImportResource({"classpath:config/spring-context.xml"})
public class Bootstrap extends WebMvcConfigurerAdapter implements SchedulingConfigurer{

    static{
        
        Log4k8s.init("account-service");
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
    
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(30);
    }
    
    @Bean  
    public FilterRegistrationBean  filterRegistrationBean() {  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/*");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }

    @Bean
    public MvcTraceFilter traceFilterRegiesty() {     return new MvcTraceFilter(); }
}

