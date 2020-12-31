package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookCategoryRepository;
import com.abouerp.zsc.library.domain.BookCategory;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    public BookCategoryService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public BookCategory save(BookCategory category){
        return bookCategoryRepository.save(category);
    }

    public void delete(Integer id){
        bookCategoryRepository.deleteById(id);
    }



}
