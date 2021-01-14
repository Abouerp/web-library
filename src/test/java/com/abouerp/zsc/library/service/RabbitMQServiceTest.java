package com.abouerp.zsc.library.service;


import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMQServiceTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {

        amqpAdmin.declareExchange(new FanoutExchange(RabbitMqConfiguration.EXCHANGE_NAME));
        amqpAdmin.declareQueue(new Queue(RabbitMqConfiguration.QUEUE_NAME));
        //将队列分别于交换器进行绑定
        amqpAdmin.declareBinding(new Binding(RabbitMqConfiguration.QUEUE_NAME,
                Binding.DestinationType.QUEUE, RabbitMqConfiguration.EXCHANGE_NAME, "", null));

    }

    @Test
    public void send(){
        Book book = new Book().setAuthor("哈哈")
                .setCode("xx")
                .setDescription("sss")
                .setIsbn("dssd")
                .setName("springboot")
                .setPublicationTime("12.47.5")
                .setId(1)
                .setBookCategory(new BookCategory().setCode("21").setName("hss").setId(1));
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME,"", book);
    }

    @Test
    public void receive(){
        Book book = (Book) rabbitTemplate.receiveAndConvert(RabbitMqConfiguration.QUEUE_NAME);
        System.out.println(book);
    }
}