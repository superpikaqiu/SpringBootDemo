package com.lwb.demo;

import com.lwb.demo.domain.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.lwb.demo")
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

        //查看容器里的组件
//        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

        for(String name : run.getBeanDefinitionNames()){
            System.out.println(name);
        }

        boolean tom = run.containsBean("tom");
        System.out.println("exist tom:" + tom);

        boolean user01 = run.containsBean("user01");
        System.out.println("exist user01:" + user01);

//        User user02 = run.getBean("user01", User.class);
//        System.out.println(user02);
    }

}
