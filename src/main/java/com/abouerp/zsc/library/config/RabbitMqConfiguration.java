package com.abouerp.zsc.library.config;

import org.springframework.amqp.core.*;
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

    public final static String EXCHANGE_NAME = "fanout_library";
    public final static String QUEUE_NAME = "fanout_library_elasticsearch";

    /**
     * 将消息转为json
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue(RabbitMqConfiguration.QUEUE_NAME);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding confirmTestFanoutExchangeAndQueue(){
        return new Binding(RabbitMqConfiguration.QUEUE_NAME,
                Binding.DestinationType.QUEUE, RabbitMqConfiguration.EXCHANGE_NAME, "", null);
    }

}
