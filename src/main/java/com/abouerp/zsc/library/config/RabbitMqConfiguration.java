package com.abouerp.zsc.library.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abouerp
 */
@Configuration
@EnableRabbit    //开启基于注解的的rabbit
public class RabbitMqConfiguration {

    private final AmqpAdmin amqpAdmin;
    public final static String EXCHANGE_NAME = "fanout_library";
    public final static String QUEUE_NAME = "fanout_library_elasticsearch";

    public RabbitMqConfiguration(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }
    /**
     * 将消息转为json
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }





}
