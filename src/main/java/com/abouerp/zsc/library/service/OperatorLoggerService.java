package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.OperatorLoggerRepository;
import com.abouerp.zsc.library.domain.logger.OperatorLogger;
import com.abouerp.zsc.library.domain.logger.QOperatorLogger;
import com.abouerp.zsc.library.utils.LoggerUtils;
import com.abouerp.zsc.library.utils.SecurityUtils;
import com.querydsl.core.BooleanBuilder;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .setPath(httpServletRequest.getRequestURI())
                .setParam(Arrays.toString(joinPoint.getArgs()))
                .setUsername(SecurityUtils.getCurrentUserLogin())
                .setSignatureName(String.format("%s.%s()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
        return operatorLogger;
    }

    public Page<OperatorLogger> findAll(OperatorLogger operatorLogger, Pageable pageable) {
        if (operatorLogger == null) {
            return operatorLoggerRepository.findAll(pageable);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QOperatorLogger qOperatorLogger = QOperatorLogger.operatorLogger;
        if (operatorLogger.getIp() != null && !operatorLogger.getIp().isEmpty()) {
            booleanBuilder.and(qOperatorLogger.ip.containsIgnoreCase(operatorLogger.getIp()));
        }
        if (operatorLogger.getHttpMethod() != null && !operatorLogger.getHttpMethod().isEmpty()) {
            booleanBuilder.and(qOperatorLogger.httpMethod.containsIgnoreCase(operatorLogger.getHttpMethod()));
        }
        if (operatorLogger.getUsername() != null && !operatorLogger.getUsername().isEmpty()) {
            booleanBuilder.and(qOperatorLogger.username.containsIgnoreCase(operatorLogger.getUsername()));
        }
        if (operatorLogger.getClient() != null && !operatorLogger.getClient().isEmpty()) {
            booleanBuilder.and(qOperatorLogger.client.containsIgnoreCase(operatorLogger.getClient()));
        }
        if (operatorLogger.getOperatingSystem() != null && !operatorLogger.getOperatingSystem().isEmpty()) {
            booleanBuilder.and(qOperatorLogger.operatingSystem.containsIgnoreCase(operatorLogger.getOperatingSystem()));
        }
        return operatorLoggerRepository.findAll(booleanBuilder, pageable);
    }
}
