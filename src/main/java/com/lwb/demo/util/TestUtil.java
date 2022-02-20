package com.lwb.demo.util;

import com.lwb.demo.domain.bean.User;

/**
 * @author lwb
 * @date 2022/2/19 17:17
 */
public class TestUtil {
    private String a;
    public void test(){

    }

    public ChildUtil getChild(){
        return new ChildUtil();
    }

    private static class  ChildUtil extends User {
        public void test(){
            System.out.println("test");
        }

        @Override
        public String getName(){
            return "test";
        }
    }
}
