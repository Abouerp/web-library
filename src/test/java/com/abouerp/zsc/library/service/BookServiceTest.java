package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookRepository;
import com.abouerp.zsc.library.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findLastBookByBookCategoryId() {
        Book book = bookRepository.findLastBookByBookCategoryId(3);
        System.out.println(book);
    }

    @Test
    public void findBookByBookCategoryId(){
        List<Book> books = bookRepository.findBookByBookCategoryId(1);
        for (Book book:books){
            System.out.println(book);
        }
    }
}