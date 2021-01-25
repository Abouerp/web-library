package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookCategory;
import com.abouerp.zsc.library.utils.JsonUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @GetMapping("/create")
    public ResultBean createExchange() {

        amqpAdmin.declareExchange(new FanoutExchange(RabbitMqConfiguration.EXCHANGE_NAME));
        amqpAdmin.declareQueue(new Queue(RabbitMqConfiguration.QUEUE_CREATE));
        //将队列分别于交换器进行绑定
        amqpAdmin.declareBinding(new Binding(RabbitMqConfiguration.QUEUE_CREATE,
                Binding.DestinationType.QUEUE, RabbitMqConfiguration.EXCHANGE_NAME, "", null));
        return ResultBean.ok();
    }

    @GetMapping("/test-send")
    public ResultBean send(){
        Book book = new Book().setAuthor("哈哈")
                .setCode("xx")
                .setDescription("sss")
                .setIsbn("dssd")
                .setName("springboot")
                .setPublicationTime("12.47.5")
                .setId(1)
                .setBookCategory(new BookCategory()
                        .setCode("21")
                        .setName("hss")
                        .setId(1)
                        .setCreateTime(Instant.now())
                        .setUpdateTime(Instant.now())
                        .setCreateBy("haha")
                        .setUpdateBy("xixi")
                )
                .setCreateTime(Instant.now())
                .setUpdateTime(Instant.now());
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME,"", JsonUtils.writeValueAsString(book));
        return ResultBean.ok();
    }

    @GetMapping("/delete")
    public ResultBean delete(){
        amqpAdmin.deleteQueue(RabbitMqConfiguration.QUEUE_CREATE);
        amqpAdmin.deleteExchange(RabbitMqConfiguration.EXCHANGE_NAME);
        return ResultBean.ok();
    }
}
