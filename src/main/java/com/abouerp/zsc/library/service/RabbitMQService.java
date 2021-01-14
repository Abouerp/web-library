package com.abouerp.zsc.library.service;


import com.abouerp.zsc.library.domain.Book;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void send(Book book){
//        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME,"",);
    }
}
