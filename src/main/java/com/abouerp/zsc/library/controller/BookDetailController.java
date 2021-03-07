package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.BookStatus;
import com.abouerp.zsc.library.mapper.BookDetailMapper;
import com.abouerp.zsc.library.service.BookDetailService;
import com.abouerp.zsc.library.vo.BookDetailVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book-detail")
public class BookDetailController {

    private final BookDetailService bookDetailService;

    public BookDetailController(BookDetailService bookDetailService) {
        this.bookDetailService = bookDetailService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK_DETAIL_CREATE')")
    public ResultBean save(@RequestBody BookDetailVO bookDetailVO) {
        return ResultBean.ok(BookDetailMapper.INSTANCE.toDTO(bookDetailService.save(bookDetailVO)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_DETAIL_UPDATE')")
    public ResultBean update(@PathVariable Integer id, @RequestBody Optional<BookDetailVO> bookDetailVO) {
        return ResultBean.ok(BookDetailMapper.INSTANCE.toDTO(bookDetailService.update(id, bookDetailVO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_DETAIL_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        bookDetailService.delete(id);
        return ResultBean.ok();
    }

    /**
     * 根据图书的id查看其下所有图书
     *
     * @param bookDetailVO.bookId 图书的id
     */
    @GetMapping
    @PreAuthorize("hasAuthority('BOOK_DETAIL_READ')")
    public ResultBean findAll(BookDetailVO bookDetailVO,
                              @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBean.ok(bookDetailService.findAll(pageable, bookDetailVO).map(BookDetailMapper.INSTANCE::toDTO));
    }

    /**
     * 获取图书的状态
     */
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('BOOK_DETAIL_CREATE')")
    public ResultBean getBookStatus() {
        return ResultBean.ok(BookStatus.mappers);
    }
}
