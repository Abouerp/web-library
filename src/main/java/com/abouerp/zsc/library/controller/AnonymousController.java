package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookDetail;
import com.abouerp.zsc.library.mapper.BookDetailMapper;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.service.BookDetailService;
import com.abouerp.zsc.library.service.ProblemManageService;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/anonymous")
public class AnonymousController {

    private final ProblemManageService problemManageService;
    private final BookRepository bookRepository;
    private final BookDetailService bookDetailService;
    private final BookSearchRepository bookSearchRepository;

    public AnonymousController(ProblemManageService problemManageService,
                               BookRepository bookRepository,
                               BookSearchRepository bookSearchRepository,
                               BookDetailService bookDetailService) {
        this.problemManageService = problemManageService;
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
        this.bookDetailService = bookDetailService;
    }

    @GetMapping("/sync")
    public ResultBean sync() {
        List<Book> list = bookRepository.findAll();
        bookSearchRepository.deleteAll();
        bookSearchRepository.saveAll(list);
        return ResultBean.ok();
    }

    @GetMapping("/problem")
    public ResultBean findAllProblem(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        ProblemManageVO problemManageVO = new ProblemManageVO();
        problemManageVO.setShow(true);
        return ResultBean.ok(problemManageService.findAll(pageable, problemManageVO));
    }

    /**
     * 获取图书的详细信息，包括馆藏信息
     * @param id  图书的id
     */
    @GetMapping("/book/{id}")
    public ResultBean findBookAllMessageById(@PathVariable Integer id) {
        List<BookDetail> bookDetails = bookDetailService.findByBookId(id);
        Map<String, Object> map = new HashMap<>();
        if (bookDetails.size() != 0) {
            map.put("book", bookDetails.get(0).getBook());
            map.put("detail", bookDetails.stream().map(BookDetailMapper.INSTANCE::toDTO).collect(Collectors.toList()));
        }else {
            map.put("book",bookRepository.findById(id));
        }
        return ResultBean.ok(map);
    }
}
