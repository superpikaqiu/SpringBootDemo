package com.lwb.demo.config;

import com.alibaba.fastjson.JSONObject;
import com.lwb.demo.domain.bean.Test;
import com.lwb.demo.domain.bean.TestChild;
import com.lwb.demo.domain.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lwb
 * @description 配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * @date 2022/1/25 21:31
 * proxyBeanMethods：代理bean的方法
 * Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 * Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 * 组件依赖必须使用Full模式默认。其他默认是否Lite模式
 */
@Configuration(proxyBeanMethods = true)
//@EnableConfigurationProperties(User.class)
public class MyConfig {

    /**
     * 外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     */
    @Bean(name = "tom")
    public User user02(){
        User a = new User();
        return a;
    }


    @Bean
    public User user01(){
        return new User();
    }

    @Bean
    public User user02(Test test){
        return new User();
    }

    @Bean
    public Test test(){
        return new Test();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, TestChild>() {

                    @Override
                    public TestChild convert(String source) {
                        TestChild testChild = new TestChild();
                        String[] split = source.split(",");
                        testChild.setA(split[0]);
                        testChild.setB(split[1]);
//                        TestChild test = JSONObject.parseObject(source, TestChild.class);
                        return testChild;
                    }
                });
            }
        };
    }












}
