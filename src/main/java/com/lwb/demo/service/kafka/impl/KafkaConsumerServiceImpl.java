package com.lwb.demo.service.kafka.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwb.demo.dto.kafka.MessageDto;
import com.lwb.demo.service.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author lwb
 * @date 2020/11/26 15:46
 * @Description TODO
 */
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        //factory.setAckDiscarded(true);
        // 消息过滤策略
//        factory.setRecordFilterStrategy(consumerRecord -> {
//            if (Integer.parseInt(consumerRecord.value().toString()) % 2 == 0) {
//                return false;
//            }
//            //返回true消息则被过滤
//            return true;
//        });

        factory.setAutoStartup(false);
        return factory;
    }


    @Override
    public void getMessage() {

    }

    @KafkaListener(id = "consumer1",topics = {"topic1"},containerFactory = "filterContainerFactory")
    public void onMessage(ConsumerRecord<?,?> record) {
        String value = (String)record.value();
        MessageDto messageDto =  JSONObject.parseObject(value,MessageDto.class);

        System.out.println("消费：" + record.topic()+ " - " + record.partition() + " - " + record.value());
    }

    @KafkaListener(id = "timingConsumer",topics = {"topic2"},containerFactory = "filterContainerFactory")
    public void onMessage2(ConsumerRecord<?,?> record){
        System.out.println(record.value());
    }


    public void startListener(){
        System.out.println("启动监听器");
        if(!kafkaListenerEndpointRegistry.getListenerContainer("consumer1").isRunning()){
            kafkaListenerEndpointRegistry.getListenerContainer("consumer1").start();
        }

        if(kafkaListenerEndpointRegistry.getListenerContainer("consumer1").isContainerPaused()){
            kafkaListenerEndpointRegistry.getListenerContainer("consumer1").resume();
        }
    }


    public void stopListener(){
        System.out.println("关闭监听器");
        kafkaListenerEndpointRegistry.getListenerContainer("consumer1").pause();
    }

}
