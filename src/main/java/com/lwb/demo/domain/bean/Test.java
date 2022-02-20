package com.lwb.demo.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lwb
 * @date 2022/1/27 22:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private String english;
    private String math;

    private TestChild child;
}
