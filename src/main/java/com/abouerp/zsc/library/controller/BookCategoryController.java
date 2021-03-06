package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.BookCategory;
import com.abouerp.zsc.library.dto.BookCategoryDTO;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.exception.BookCategoryNotFoundException;
import com.abouerp.zsc.library.mapper.BookCategoryMapper;
import com.abouerp.zsc.library.mapper.BookMapper;
import com.abouerp.zsc.library.service.BookCategoryService;
import com.abouerp.zsc.library.vo.BookCategoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static BookCategory update(BookCategory bookCategory, Optional<BookCategoryVO> bookCategoryVO) {
        bookCategoryVO.map(BookCategoryVO::getName).ifPresent(bookCategory::setName);
        bookCategoryVO.map(BookCategoryVO::getCode).ifPresent(bookCategory::setCode);
        return bookCategory;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK_CATEGORY_CREATE')")
    public ResultBean<BookCategoryDTO> save(@RequestBody BookCategoryVO bookCategoryVO) {
        BookCategory exist = bookCategoryService.findByCode(bookCategoryVO.getCode());
        if (exist != null) {
            return ResultBean.of(200, "BookCategory Code is Exist");
        }
        BookCategory bookCategory = bookCategoryService.save(BookCategoryMapper.INSTANCE.toModle(bookCategoryVO));
        return ResultBean.ok(BookCategoryMapper.INSTANCE.toDTO(bookCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_CATEGORY_UPDATE')")
    public ResultBean<BookCategoryDTO> update(@PathVariable Integer id,
                                              @RequestBody Optional<BookCategoryVO> bookCategoryVO) {
        BookCategory bookCategory = bookCategoryService.findById(id)
                .orElseThrow(BookCategoryNotFoundException::new);
        return ResultBean.ok(BookCategoryMapper.INSTANCE.toDTO(
                bookCategoryService.save(update(bookCategory, bookCategoryVO))));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BOOK_CATEGORY_READ')")
    public ResultBean<Page<BookCategoryDTO>> findAll(
            @PageableDefault Pageable pageable,
            BookCategoryVO bookCategoryVO) {
        return ResultBean.ok(bookCategoryService.findAll(bookCategoryVO, pageable).map(BookCategoryMapper.INSTANCE::toDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_CATEGORY_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        List<BookDTO> dtoList = bookCategoryService.delete(id).stream().map(BookMapper.INSTANCE::toDTO).collect(Collectors.toList());
        if (dtoList.size() == 0) {
            return ResultBean.ok();
        }
        return ResultBean.of(200, "Some books exist in this category", dtoList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_CATEGORY_READ')")
    public ResultBean<BookCategoryDTO> findById(@PathVariable Integer id) {
        BookCategory bookCategory = bookCategoryService.findById(id).orElseThrow(BookCategoryNotFoundException::new);
        return ResultBean.ok(BookCategoryMapper.INSTANCE.toDTO(bookCategory));
    }
}
