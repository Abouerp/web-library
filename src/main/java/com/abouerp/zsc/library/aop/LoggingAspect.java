package com.abouerp.zsc.library.aop;

import com.abouerp.zsc.library.domain.logger.OperatorStatusEnum;
import com.abouerp.zsc.library.service.OperatorLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Date;

/**
 * @author Abouerp
 */
@Slf4j
@Aspect
public class LoggingAspect {

    private final OperatorLoggerService operatorLoggerService;
    private Date visitTime;

    public LoggingAspect(OperatorLoggerService operatorLoggerService) {
        this.operatorLoggerService = operatorLoggerService;
    }

    @Pointcut(value = "within(com.abouerp.zsc.library.controller..*) ")
    public void applicationPackagePointcut() {
        // ...&& execution(public * com.abouerp.zsc.library.controller.AdministratorController.me())" +
        //            "&& execution(public * com.abouerp.zsc.library.controller.LoggerController.findAllOperator())"
    }

    @Before("applicationPackagePointcut()")
    public void beforeLogAround() {
        visitTime = new Date();
    }

    /**
     * 若连接点抛出异常则不会执行
     * @param joinPoint
     */
    @AfterReturning("applicationPackagePointcut()")
    public void afterLogAround(JoinPoint joinPoint) {
        operatorLoggerService.recode(joinPoint, new Date().getTime() - visitTime.getTime(), OperatorStatusEnum.SUCCESS);
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()")
    public void exception(JoinPoint joinPoint){
        operatorLoggerService.recode(joinPoint, new Date().getTime() - visitTime.getTime(), OperatorStatusEnum.FAIL);
    }
}
