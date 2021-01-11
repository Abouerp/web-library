package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.logger.LoginLogger;
import com.abouerp.zsc.library.domain.logger.OperatorLogger;
import com.abouerp.zsc.library.service.LoginLoggerService;
import com.abouerp.zsc.library.service.OperatorLoggerService;
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
    private final OperatorLoggerService operatorLoggerService;

    public LoggerController(LoginLoggerService loginLoggerService,
                            OperatorLoggerService operatorLoggerService) {
        this.loginLoggerService = loginLoggerService;
        this.operatorLoggerService = operatorLoggerService;
    }

    @GetMapping("/login")
    public ResultBean<Page<LoginLogger>> findAllLogin(@PageableDefault Pageable pageable, LoginLogger loginLogger){
        return ResultBean.ok(loginLoggerService.findAll(loginLogger,pageable));
    }

    @GetMapping("/operator")
    public ResultBean<Page<OperatorLogger>> findAllOperator(@PageableDefault Pageable pageable, OperatorLogger operatorLogger){
        return ResultBean.ok(operatorLoggerService.findAll(operatorLogger,pageable));
    }

}
