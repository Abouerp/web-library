package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.BookCategory;
import com.abouerp.zsc.library.dto.BookCategoryDTO;
import com.abouerp.zsc.library.exception.BookCategoryNotFoundException;
import com.abouerp.zsc.library.mapper.BookCategoryMapper;
import com.abouerp.zsc.library.service.BookCategoryService;
import com.abouerp.zsc.library.vo.AdministratorVO;
import com.abouerp.zsc.library.vo.BookCategoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    private static BookCategory update(BookCategory bookCategory, Optional<BookCategoryVO> bookCategoryVO){
        bookCategoryVO.map(BookCategoryVO::getName).ifPresent(bookCategory::setName);
        bookCategoryVO.map(BookCategoryVO::getCode).ifPresent(bookCategory::setCode);
        return bookCategory;
    }

    @PostMapping
    public ResultBean<BookCategoryDTO> save(@RequestBody BookCategoryVO bookCategoryVO){
        BookCategory bookCategory = bookCategoryService.save(BookCategoryMapper.INSTANCE.toModle(bookCategoryVO));
        return ResultBean.ok(BookCategoryMapper.INSTANCE.toDTO(bookCategory));
    }

    @PutMapping("/{id}")
    public ResultBean<BookCategoryDTO> update(@PathVariable Integer id,
                                              @RequestBody Optional<BookCategoryVO> bookCategoryVO){
        BookCategory bookCategory = bookCategoryService.findById(id)
                .orElseThrow(BookCategoryNotFoundException::new);
        return ResultBean.ok(BookCategoryMapper.INSTANCE.toDTO(
                bookCategoryService.save(update(bookCategory,bookCategoryVO))));
    }

    @GetMapping
    public ResultBean<Page<BookCategoryDTO>> findAll(
            @PageableDefault Pageable pageable,
            BookCategoryVO bookCategoryVO){
           return ResultBean.ok(bookCategoryService.findAll(bookCategoryVO,pageable).map(BookCategoryMapper.INSTANCE::toDTO));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id){
        return ResultBean.ok();
    }
}
