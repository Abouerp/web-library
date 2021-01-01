package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.service.BookCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book-category")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }


}
