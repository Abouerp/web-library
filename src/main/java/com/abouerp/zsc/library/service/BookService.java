package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.domain.book.QBook;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.mapper.BookMapper;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.vo.BookVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @author Abouerp
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RabbitMQService rabbitMQService;


    public BookService(BookRepository bookRepository,
                       RabbitMQService rabbitMQService) {
        this.bookRepository = bookRepository;
        this.rabbitMQService = rabbitMQService;
    }

    public BookDTO save(Book book) {
        book = bookRepository.save(book);
        BookDTO bookDTO = BookMapper.INSTANCE.toDTO(book);
        rabbitMQService.produceCreate(book);
        return bookDTO;
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    public void delete(Set<Integer> ids) {
        bookRepository.deleteByIdIn(ids);
        rabbitMQService.produceDelete(ids);
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
        if (bookVO.getBookCategoryId() != null) {
            booleanBuilder.and(qBook.bookCategory.id.eq(bookVO.getBookCategoryId()));
        }
        return bookRepository.findAll(booleanBuilder, pageable);
    }

    public Book findLastBookByBookCategoryId(Integer id) {
        return bookRepository.findLastBookByBookCategoryId(id);
    }

}
