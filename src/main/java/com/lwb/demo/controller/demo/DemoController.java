package com.lwb.demo.controller.demo;

import com.lwb.demo.domain.bean.Test;
import com.lwb.demo.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class  DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("/test1")
    public void test1(){
        demoService.test1();
    }

    @GetMapping("/test")
    public String test(){
        return "forward:/demo/success";
    }

    @GetMapping("/success")
    public void success(){
        System.out.println("success");
    }

    @PostMapping
    public void postTest(Test test){
        System.out.println(test);
    }

    // /cars/sell;low=34;brand=a,b,c
    @GetMapping("/cars/{path}")
    public void matrix(@MatrixVariable("low") Integer low, @MatrixVariable("brand") List<String> brand, @PathVariable("path") String path){
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        System.out.println(map);
    }
}
