package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookDetailRepository;

import com.abouerp.zsc.library.domain.BookStatus;
import com.abouerp.zsc.library.vo.BookDetailVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Abouerp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDetailServiceTest {

    @Autowired
    BookDetailRepository bookDetailRepository;
    @Autowired
    BookDetailService bookDetailService;

    @Test
    public void save() {
//        for (int i = 0; i < 10; i++) {
//            BookDetailVO bookDetailVO = new BookDetailVO();
//            bookDetailVO.setAddress("加拿大"+i);
//            bookDetailVO.setBorrowingTimes(3+i);
//            bookDetailVO.setRenewalTimes(2);
//            bookDetailVO.setStatus(BookStatus.IN_LIBRARY);
//            bookDetailVO.setBookId(1);
//            System.out.println(bookDetailService.save(bookDetailVO));
//        }
    }

    @Test
    public void delete() {
    }
}