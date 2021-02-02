package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.domain.book.QBookCategory;
import com.abouerp.zsc.library.repository.BookCategoryRepository;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookCategory;
import com.abouerp.zsc.library.vo.BookCategoryVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;

    public BookCategoryService(BookCategoryRepository bookCategoryRepository,
                               BookRepository bookRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookRepository = bookRepository;

    }

    public Optional<BookCategory> findById(Integer id) {
        return bookCategoryRepository.findById(id);
    }

    public BookCategory save(BookCategory category) {
        return bookCategoryRepository.save(category);
    }

    public List<Book> delete(Integer id) {
        List<Book> bookList = bookRepository.findBookByBookCategoryId(id);
        if (bookList == null || bookList.size() == 0) {
            bookCategoryRepository.deleteById(id);
        }
        return bookList;
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

    public BookCategory findByCode(String code) {
        return bookCategoryRepository.findByCode(code);
    }

}
