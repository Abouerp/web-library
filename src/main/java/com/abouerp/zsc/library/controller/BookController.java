package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookCategory;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.exception.BookCategoryNotFoundException;
import com.abouerp.zsc.library.exception.BookNotFoundException;
import com.abouerp.zsc.library.mapper.BookMapper;
import com.abouerp.zsc.library.service.BookCategoryService;
import com.abouerp.zsc.library.service.BookService;
import com.abouerp.zsc.library.service.FileStorageService;
import com.abouerp.zsc.library.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

/**
 * @author Abouerp
 */
@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final BookCategoryService bookCategoryService;
    private final FileStorageService fileStorageService;

    public BookController(BookService bookService, BookCategoryService bookCategoryService,
                          FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
        this.fileStorageService = fileStorageService;
    }

    private static Book update(Book book, Optional<BookVO> bookVO) {
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
    public ResultBean<BookDTO> save(@RequestBody BookVO bookVO) {
        BookCategory bookCategory = bookCategoryService.findById(bookVO.getBookCategoryId())
                .orElseThrow(BookCategoryNotFoundException::new);
        Book book = BookMapper.INSTANCE.toBook(bookVO);
        Book lastBook = bookService.findLastBookByBookCategoryId(bookCategory.getId());
        if (lastBook == null) {
            book.setCode(String.format(bookCategory.getCode() + "0001"));
        } else {
            Integer code = Integer.parseInt(lastBook.getCode().substring(4));
            String finallyCode = String.format(bookCategory.getCode() + "%04d", code + 1);
            book.setCode(finallyCode);
        }
        book.setBookCategory(bookCategory);
        return ResultBean.ok(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResultBean<BookDTO> update(@PathVariable Integer id, @RequestBody Optional<BookVO> bookVO) {
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        Integer bookCategoryId = bookVO.get().getBookCategoryId();
        bookVO.map(BookVO::getBookCategoryId).ifPresent(it ->
                book.setBookCategory(bookCategoryService.findById(bookCategoryId).get())
        );
        return ResultBean.ok(bookService.save(update(book, bookVO)));
    }

    @GetMapping
    public ResultBean<Page<BookDTO>> findAll(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
            BookVO bookVO) {
        return ResultBean.ok(bookService.findAll(bookVO, pageable).map(BookMapper.INSTANCE::toDTO));
    }

    @DeleteMapping
    public ResultBean delete(@RequestBody Set<Integer> ids) {
        bookService.delete(ids);
        return ResultBean.ok();
    }

    @GetMapping("/{id}")
    public ResultBean<BookDTO> findById(@PathVariable Integer id) {
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        return ResultBean.ok(BookMapper.INSTANCE.toDTO(book));
    }

    /**
     * 解析excel文件中的图书数据返给前端
     */
    @PostMapping("/excel")
    public ResultBean analysisExcel(@RequestParam MultipartFile file) {
        return ResultBean.ok(fileStorageService.analysisExcel(file));
    }
}
