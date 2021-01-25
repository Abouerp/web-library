package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Slf4j
@Service
public class RabbitMQService {
    private final BookSearchRepository bookSearchRepository;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(
            BookSearchRepository bookSearchRepository,
            RabbitTemplate rabbitTemplate) {
        this.bookSearchRepository = bookSearchRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceCreate(Book book) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.QUEUE_CREATE, JsonUtils.writeValueAsString( book));
    }

//    public void produceUpdate(Book book){
//        rabbitTemplate.convertAndSend(RabbitMqConfiguration.QUEUE_UPDATE, book);
//    }
//
//    public void produceDelete(Integer id){
//        rabbitTemplate.convertAndSend(RabbitMqConfiguration.QUEUE_DELETE,"delete", id);
//    }

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_CREATE)
    public void receiveCreate(String book){
        Book saveBook = JsonUtils.readValue(book, Book.class);
        bookSearchRepository.save(saveBook);
    }

//    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_UPDATE)
//    public void receiveUpdate(String message){
//        //保存队列
//        Book book = JsonUtils.readValue(message, Book.class);
//
//    }
//
//    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_DELETE)
//    public void receiveDelete(Message message){
//        //保存队列
//        Book book = JsonUtils.readValue(message.getBody(), Book.class);
//        log.info("执行了删除-------------");
////        bookSearchRepository.save(book);
////        log.info("接收到的消息：{}",JsonUtils.readValue(message.getBody(), Book.class));
//    }
}
