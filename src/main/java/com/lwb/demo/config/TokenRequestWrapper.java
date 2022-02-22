package com.lwb.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author lwb
 * @date 2022/1/17 10:16
 * @description TODO
 */

public class TokenRequestWrapper extends HttpServletRequestWrapper {

    public TokenRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }
}
