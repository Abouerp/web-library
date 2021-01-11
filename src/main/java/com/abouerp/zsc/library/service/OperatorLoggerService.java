package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.OperatorLoggerRepository;
import com.abouerp.zsc.library.domain.logger.OperatorLogger;
import com.abouerp.zsc.library.utils.LoggerUtils;
import com.abouerp.zsc.library.utils.SecurityUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Abouerp
 */
@Slf4j
@Service
public class OperatorLoggerService {

    private final OperatorLoggerRepository operatorLoggerRepository;
    private final HttpServletRequest httpServletRequest;

    public OperatorLoggerService(OperatorLoggerRepository operatorLoggerRepository,
                                 HttpServletRequest httpServletRequest) {
        this.operatorLoggerRepository = operatorLoggerRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Async
    public void recode(JoinPoint joinPoint, long executionTime) {
        OperatorLogger operatorLogger = toOperatorLogger(joinPoint, executionTime);
        operatorLoggerRepository.save(operatorLogger);
    }

    private OperatorLogger toOperatorLogger(JoinPoint joinPoint, long executionTime) {
        UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        OperatorLogger operatorLogger = new OperatorLogger()
                .setIp(LoggerUtils.getClientIpAddress(httpServletRequest))
                .setClient(userAgent.getBrowser().getName())
                .setExecutionTime(executionTime)
                .setHttpMethod(httpServletRequest.getMethod())
                .setOperatingSystem(userAgent.getOperatingSystem().getName())
                .setPath(httpServletRequest.getRequestURL().toString())
                .setParam(Arrays.toString(joinPoint.getArgs()))
                .setUsername(SecurityUtils.getCurrentUserLogin())
                .setSignatureName(String.format("%s.%s()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
        return operatorLogger;
    }
}
