package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.BookDetailRepository;
import com.abouerp.zsc.library.dao.BookRepository;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookDetail;
import com.abouerp.zsc.library.domain.QBookDetail;
import com.abouerp.zsc.library.exception.BookDetailNotFoundException;
import com.abouerp.zsc.library.exception.BookNotFoundException;
import com.abouerp.zsc.library.mapper.BookDetailMapper;
import com.abouerp.zsc.library.vo.BookDetailVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class BookDetailService {

    private final BookDetailRepository bookDetailRepository;
    private final BookRepository bookRepository;


    public BookDetailService(BookDetailRepository bookDetailRepository,
                             BookRepository bookRepository) {
        this.bookDetailRepository = bookDetailRepository;
        this.bookRepository = bookRepository;
    }

    private BookDetail update(BookDetail bookDetail, Optional<BookDetailVO> bookDetailVO) {
        bookDetailVO.map(BookDetailVO::getAddress).ifPresent(bookDetail::setAddress);
        bookDetailVO.map(BookDetailVO::getBackTime).ifPresent(bookDetail::setBackTime);
        bookDetailVO.map(BookDetailVO::getRenewalTimes).ifPresent(bookDetail::setRenewalTimes);
        bookDetailVO.map(BookDetailVO::getBorrowingTimes).ifPresent(bookDetail::setBorrowingTimes);
        bookDetailVO.map(BookDetailVO::getStatus).ifPresent(bookDetail::setStatus);
        return bookDetail;
    }

    @Transactional
    public BookDetail save(BookDetailVO bookDetailVO) {
        Book book = bookRepository.findById(bookDetailVO.getBookId()).orElseThrow(BookNotFoundException::new);
        BookDetail bookDetail = BookDetailMapper.INSTANCE.toBookDetale(bookDetailVO);
        BookDetail lastBookDetail = bookDetailRepository.findLastBookDetailByBookId(bookDetailVO.getBookId());
        if (lastBookDetail == null) {
            bookDetail.setSearchCode(String.format(book.getCode() + "01"));
        } else {
            Integer code = Integer.parseInt(lastBookDetail.getSearchCode().substring(8));
            String finallyCode = String.format(book.getCode() + "%02d", code + 1);
            bookDetail.setSearchCode(finallyCode);
        }
        bookDetail.setBook(book);
        return bookDetailRepository.save(bookDetail);
    }

    public void delete(Integer id) {
        bookDetailRepository.deleteById(id);
    }

    public BookDetail update(Integer id, Optional<BookDetailVO> bookDetailVO) {
        BookDetail bookDetail = bookDetailRepository.findById(id).orElseThrow(BookDetailNotFoundException::new);
        return bookDetailRepository.save(update(bookDetail, bookDetailVO));
    }

    public Page<BookDetail> findAll( Pageable pageable, BookDetailVO bookDetailVO) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBookDetail qBookDetail = QBookDetail.bookDetail;
        if (bookDetailVO.getBookId() != null) {
            booleanBuilder.and(qBookDetail.book.id.eq(bookDetailVO.getBookId()));
        }
        if (bookDetailVO.getAddress() != null && !bookDetailVO.getAddress().isEmpty()) {
            booleanBuilder.and(qBookDetail.address.containsIgnoreCase(bookDetailVO.getAddress()));
        }
        if (bookDetailVO.getStatus() != null) {
            booleanBuilder.and(qBookDetail.status.eq(bookDetailVO.getStatus()));
        }
        if (bookDetailVO.getSearchCode() != null && !bookDetailVO.getSearchCode().isEmpty()) {
            booleanBuilder.or(qBookDetail.searchCode.like(String.format("&"+bookDetailVO.getSearchCode()+"%")));
        }
        return bookDetailRepository.findAll(booleanBuilder, pageable);
    }

}
