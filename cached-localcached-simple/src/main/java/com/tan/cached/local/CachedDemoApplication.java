package com.tan.cached.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**http://www.jianshu.com/p/453c6e7ff81c  
 * http://blog.csdn.net/chunlongyu/article/category/6638499
 * @author Administrator
 *
 */
//@SpringBootApplication
@Controller
//@EnableAutoConfiguration
public class CachedDemoApplication {

	public static void main(String[] args) {
	//SpringApplication.run(RocketmqDemoApplication.class, args);
	}
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}
