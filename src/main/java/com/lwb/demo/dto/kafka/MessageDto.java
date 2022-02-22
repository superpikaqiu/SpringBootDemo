package com.lwb.demo.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwb
 * @date 2022/2/21 17:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String id;
    private String name;
    private BigDecimal money;
    private Date createTime;
}
