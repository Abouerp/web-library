package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.service.ProblemManageService;
import com.abouerp.zsc.library.vo.ProblemManageVO;
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
@RequestMapping("/api/problem-manage")
public class ProblemManageController {

    private final ProblemManageService problemManageService;

    public ProblemManageController(ProblemManageService problemManageService) {
        this.problemManageService = problemManageService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PROBLEM_MANAGE_CREATE')")
    public ResultBean save(@RequestBody ProblemManageVO problemManageVO) {
        return ResultBean.ok(problemManageService.save(problemManageVO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PROBLEM_MANAGE_UPDATE')")
    public ResultBean update(@PathVariable Integer id, @RequestBody Optional<ProblemManageVO> problemManageVO) {
        return ResultBean.ok(problemManageService.update(id, problemManageVO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PROBLEM_MANAGE_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        problemManageService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PROBLEM_MANAGE_READ')")
    public ResultBean findAll(ProblemManageVO problemManageVO,
                              @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBean.ok(problemManageService.findAll(pageable, problemManageVO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROBLEM_MANAGE_READ')")
    public ResultBean findById(@PathVariable Integer id) {
        return ResultBean.ok(problemManageService.findById(id));
    }
}
