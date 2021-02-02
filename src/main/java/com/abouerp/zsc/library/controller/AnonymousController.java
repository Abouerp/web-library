package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.service.ProblemManageService;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/anonymous")
public class AnonymousController {

    private final ProblemManageService problemManageService;
    private final BookRepository bookRepository;
    private final BookSearchRepository bookSearchRepository;

    public AnonymousController(ProblemManageService problemManageService,
                               BookRepository bookRepository,
                               BookSearchRepository bookSearchRepository) {
        this.problemManageService = problemManageService;
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
    }

    @GetMapping("/sync")
    public ResultBean sync() {
        List<Book> list = bookRepository.findAll();
        bookSearchRepository.deleteAll();
        bookSearchRepository.saveAll(list);
        return ResultBean.ok();
    }

    @GetMapping("/problem")
    public ResultBean findAllProblem(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable){
        ProblemManageVO problemManageVO = new ProblemManageVO();
        problemManageVO.setShow(true);
        return ResultBean.ok(problemManageService.findAll(pageable, problemManageVO));
    }
}
