package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.logger.LoginLogger;
import com.abouerp.zsc.library.service.LoginLoggerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/logger")
public class LoggerController {

    private final LoginLoggerService loginLoggerService;

    public LoggerController(LoginLoggerService loginLoggerService) {
        this.loginLoggerService = loginLoggerService;
    }

    @GetMapping("/login")
    public ResultBean<Page<LoginLogger>> findAll(@PageableDefault Pageable pageable, LoginLogger loginLogger){
        return ResultBean.ok(loginLoggerService.findAll(loginLogger,pageable));
    }
}
