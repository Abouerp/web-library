package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookCategory;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.exception.BookCategoryNotFoundException;
import com.abouerp.zsc.library.exception.BookNotFoundException;
import com.abouerp.zsc.library.mapper.BookMapper;
import com.abouerp.zsc.library.service.BookCategoryService;
import com.abouerp.zsc.library.service.BookService;
import com.abouerp.zsc.library.vo.BookVO;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final BookCategoryService bookCategoryService;

    public BookController(BookService bookService, BookCategoryService bookCategoryService) {
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
    }

    private static Book update(Book book, Optional<BookVO> bookVO){
        bookVO.map(BookVO::getAddress).ifPresent(book::setAddress);
        bookVO.map(BookVO::getCode).ifPresent(book::setCode);
        bookVO.map(BookVO::getName).ifPresent(book::setName);
        bookVO.map(BookVO::getAuthor).ifPresent(book::setAuthor);
        bookVO.map(BookVO::getDescription).ifPresent(book::setDescription);
        bookVO.map(BookVO::getIsbn).ifPresent(book::setIsbn);
        bookVO.map(BookVO::getPrice).ifPresent(book::setPrice);
        bookVO.map(BookVO::getPublicationTime).ifPresent(book::setPublicationTime);
        bookVO.map(BookVO::getPublisher).ifPresent(book::setPublisher);
        return book;
    }

    @PostMapping
    public ResultBean<BookDTO> save(@RequestBody BookVO bookVO){
        BookCategory bookCategory = bookCategoryService.findById(bookVO.getBookCategoryId())
                .orElseThrow(BookCategoryNotFoundException::new);
        Book book = BookMapper.INSTANCE.toBook(bookVO);
        book.setBookCategory(bookCategory);
        return ResultBean.ok(BookMapper.INSTANCE.toDTO(bookService.save(book)));
    }

    @PutMapping("/{id}")
    public ResultBean<BookDTO> update(@PathVariable Integer id, @RequestBody Optional<BookVO> bookVO){
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        Integer bookCategoryId = bookVO.get().getBookCategoryId();
        bookVO.map(BookVO::getBookCategoryId).ifPresent( it -> {
                book.setBookCategory(bookCategoryService.findById(bookCategoryId).get());
        });
        return ResultBean.ok(BookMapper.INSTANCE.toDTO(bookService.save(update(book,bookVO))));
    }

    @GetMapping
    public ResultBean<Page<BookDTO>> findAll(@PageableDefault Pageable pageable, BookVO bookVO){
        return ResultBean.ok(bookService.findAll(bookVO, pageable).map(BookMapper.INSTANCE::toDTO));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id){
        bookService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping("/{id}")
    public ResultBean<BookDTO> findById(@PathVariable Integer id){
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        return ResultBean.ok(BookMapper.INSTANCE.toDTO(book));
    }
}
