package com.lwb.demo.service.demo.impl;

import com.lwb.demo.domain.bean.User;
import com.lwb.demo.service.demo.DemoService;
import com.lwb.demo.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lwb
 * @date 2022/1/26 22:27
 */
@Service
public class DemoServiceImpl implements DemoService {


    @Resource
    private User user02;

    @Override
    public void test1() {
        TestUtil testUtil = new TestUtil();
        User child = testUtil.getChild();
        System.out.println(child.getName());
    }
}
