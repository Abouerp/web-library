package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookDetailRepository;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class BookDetailService {

    private final BookDetailRepository bookDetailRepository;

    public BookDetailService(BookDetailRepository bookDetailRepository) {
        this.bookDetailRepository = bookDetailRepository;
    }


}
