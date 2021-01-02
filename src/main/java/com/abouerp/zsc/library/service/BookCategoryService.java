package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookCategoryRepository;
import com.abouerp.zsc.library.domain.BookCategory;
import com.abouerp.zsc.library.domain.QBookCategory;
import com.abouerp.zsc.library.vo.BookCategoryVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    public BookCategoryService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public Optional<BookCategory> findById(Integer id) {
        return bookCategoryRepository.findById(id);
    }

    public BookCategory save(BookCategory category) {
        return bookCategoryRepository.save(category);
    }

    public void delete(Integer id) {
        bookCategoryRepository.deleteById(id);
    }

    public Page<BookCategory> findAll(BookCategoryVO bookCategoryVO, Pageable pageable) {
        QBookCategory qBookCategory = QBookCategory.bookCategory;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (bookCategoryVO == null) {
            return bookCategoryRepository.findAll(pageable);
        }
        if (bookCategoryVO.getName() != null && !bookCategoryVO.getName().isEmpty()) {
            booleanBuilder.and(qBookCategory.name.containsIgnoreCase(bookCategoryVO.getName()));
        }
        if (bookCategoryVO.getCode() != null && !bookCategoryVO.getCode().isEmpty()) {
            booleanBuilder.and(qBookCategory.code.containsIgnoreCase(bookCategoryVO.getCode()));
        }
        return bookCategoryRepository.findAll(booleanBuilder, pageable);
    }


}
