package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Slf4j
@Service
public class RabbitMQService {

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_NAME)
    public void receive(Message message){
        log.info("接收到的消息：{}",JsonUtils.readValue(message.getBody(), Book.class));
    }
}
