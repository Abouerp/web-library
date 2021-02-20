package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.logger.LoginLogger;
import com.abouerp.zsc.library.domain.logger.OperatorLogger;
import com.abouerp.zsc.library.service.LoginLoggerService;
import com.abouerp.zsc.library.service.OperatorLoggerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

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
    @PreAuthorize("hasAuthority('LOGIN_LOGGER_READ')")
    public ResultBean<Page<LoginLogger>> findAllLogin(@PageableDefault Pageable pageable, LoginLogger loginLogger) {
        return ResultBean.ok(loginLoggerService.findAll(loginLogger, pageable));
    }

    @GetMapping("/operator")
    @PreAuthorize("hasAuthority('OPERATOR_LOGGER_READ')")
    public ResultBean<Page<OperatorLogger>> findAllOperator(@PageableDefault Pageable pageable,
                                                            OperatorLogger operatorLogger,
                                                            Instant startTime,
                                                            Instant endTime) {
        if (endTime == null) {
            endTime = Instant.now();
        }
        return ResultBean.ok(operatorLoggerService.findAll(operatorLogger, pageable, startTime, endTime));
    }

}
