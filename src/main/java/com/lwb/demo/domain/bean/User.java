package com.lwb.demo.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "custom")
//@Component
public class User {
    private String name;
    private Integer age;
    private List<String> arr;
    private List<String> brr;
    private Test score;
    private Map<String, BigDecimal> pet;
    private String orgName;

}
