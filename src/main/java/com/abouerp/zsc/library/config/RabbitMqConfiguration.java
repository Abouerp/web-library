package com.abouerp.zsc.library.config;

import com.abouerp.zsc.library.utils.JsonUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
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
    public final static String QUEUE_CREATE = "fanout_library_book_create";
    public final static String QUEUE_UPDATE ="fanout_library_book_update";
    public final static String QUEUE_DELETE ="fanout_library_book_delete";

    /**
     * 将消息转为json
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter(JsonUtils.DEFAULT_MAPPER);
    }

    @Bean
    public Queue queueCreate() {
        return new Queue(QUEUE_CREATE);
    }

//    @Bean
//    public Queue queueUpdate() {
//        return new Queue(QUEUE_UPDATE);
//    }
//
//    @Bean
//    public Queue queueDelete() {
//        return new Queue(QUEUE_DELETE);
//    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding QUEUE_CREATE(){
        return new Binding(QUEUE_CREATE,
                Binding.DestinationType.QUEUE, EXCHANGE_NAME, "", null);
    }

//    @Bean
//    public Binding QUEUE_UPDATE(){
//        return new Binding(QUEUE_UPDATE,
//                Binding.DestinationType.QUEUE, EXCHANGE_NAME, "", null);
//    }
//
//    @Bean
//    public Binding QUEUE_DELETE(){
//        return new Binding(QUEUE_DELETE,
//                Binding.DestinationType.QUEUE, EXCHANGE_NAME, "delete", null);
//    }
}
