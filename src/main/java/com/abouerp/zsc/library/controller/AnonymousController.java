package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.ProblemManage;
import com.abouerp.zsc.library.service.ProblemManageService;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/anonymous")
public class AnonymousController {

    private final ProblemManageService problemManageService;

    public AnonymousController(ProblemManageService problemManageService) {
        this.problemManageService = problemManageService;
    }

    @GetMapping("/problem")
    public ResultBean findAllProblem(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable){
        ProblemManageVO problemManageVO = new ProblemManageVO();
        problemManageVO.setShow(true);
        return ResultBean.ok(problemManageService.findAll(pageable, problemManageVO));
    }
}
