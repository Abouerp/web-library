package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.QUEUE_CREATE, JsonUtils.writeValueAsString(book));
    }

    public void produceDelete(Set<Integer> ids) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.QUEUE_DELETE, JsonUtils.writeValueAsString(ids));
    }

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_CREATE)
    public void receiveCreate(String book) {
        Book saveBook = JsonUtils.readValue(book, Book.class);
        bookSearchRepository.save(saveBook);
    }

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_DELETE)
    public void receiveDelete(String ids) {
        bookSearchRepository.deleteByIdIn(JsonUtils.readValue(ids, HashSet.class));
    }
}
