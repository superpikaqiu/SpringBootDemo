package com.lwb.demo.service.kafka.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lwb.demo.dto.kafka.MessageDto;
import com.lwb.demo.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author lwb
 * @date 2020/11/26 15:46
 * @Description TODO
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void sendMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setId("1001");
        messageDto.setMoney(BigDecimal.ONE);
        messageDto.setCreateTime(new Date());
        messageDto.setName("test1001");

        String message = JSONObject.toJSONString(messageDto);

        kafkaTemplate.send("topic1",message);
        //kafkaTemplate.send("topic1","abc");
    }

    @Override
    public void sendMessage2() {
        for(int i=0;i<20;i++){
            kafkaTemplate.send("topic2",String.valueOf(i));
        }
    }


    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler(){
        return (message,exception,consumer) -> {
            System.out.println("消费异常: " + message.getPayload());
            return null;
        };
    }

}
