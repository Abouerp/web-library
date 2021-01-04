package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookRepository;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.QBook;
import com.abouerp.zsc.library.vo.BookVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> findAll(BookVO bookVO, Pageable pageable) {
        QBook qBook = QBook.book;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (bookVO == null) {
            return bookRepository.findAll(pageable);
        }
        if (bookVO.getName() != null && !bookVO.getName().isEmpty()) {
            booleanBuilder.and(qBook.name.containsIgnoreCase(bookVO.getName()));
        }
        if (bookVO.getIsbn() != null && !bookVO.getIsbn().isEmpty()) {
            booleanBuilder.and(qBook.isbn.containsIgnoreCase(bookVO.getIsbn()));
        }
        if (bookVO.getCode() != null && !bookVO.getCode().isEmpty()) {
            booleanBuilder.and(qBook.code.containsIgnoreCase(bookVO.getCode()));
        }
        if (bookVO.getPublisher() != null && !bookVO.getPublisher().isEmpty()) {
            booleanBuilder.and(qBook.publisher.containsIgnoreCase(bookVO.getPublisher()));
        }
        return bookRepository.findAll(booleanBuilder, pageable);
    }
}
