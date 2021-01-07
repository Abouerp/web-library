package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.LoginLoggerRepository;
import com.abouerp.zsc.library.domain.logger.LoginLogger;
import com.abouerp.zsc.library.domain.logger.LoginStatusEnum;
import com.abouerp.zsc.library.domain.logger.QLoginLogger;
import com.abouerp.zsc.library.dto.IpResolutionDTO;
import com.abouerp.zsc.library.utils.IpResolutionUtils;
import com.abouerp.zsc.library.utils.LoggerUtils;
import com.querydsl.core.BooleanBuilder;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Abouerp
 */
@Slf4j
@Service
public class LoginLoggerService {

    private final LoginLoggerRepository loginLoggerRepository;
    private final HttpServletRequest httpServletRequest;

    public LoginLoggerService(LoginLoggerRepository loginLoggerRepository, HttpServletRequest httpServletRequest) {
        this.loginLoggerRepository = loginLoggerRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Async
    public void fail(String s) {
        LoginLogger loginLogger = toLoginLogger(s, LoginStatusEnum.FAIL);
        loginLoggerRepository.save(loginLogger);
    }

    @Async
    public void success(String s) {
        LoginLogger loginLogger = toLoginLogger(s, LoginStatusEnum.SUCCESS);
        loginLoggerRepository.save(loginLogger);
    }

    private LoginLogger toLoginLogger(String userName, LoginStatusEnum status) {
        UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        String ip = LoggerUtils.getClientIpAddress(httpServletRequest);
        IpResolutionDTO ipResolutionDTO = IpResolutionUtils.resolution(ip);
        log.info(ipResolutionDTO.toString());
        LoginLogger loginLogger = new LoginLogger()
                .setUserName(userName)
                .setIp(ip)
                .setCountry(ipResolutionDTO.getData().getCountry())
                .setCity(ipResolutionDTO.getData().getCity())
                .setIsp(ipResolutionDTO.getData().getIsp())
                .setOperatingSystem(userAgent.getOperatingSystem().toString())
                .setClient(userAgent.getBrowser().toString())
                .setStatus(status);
        return loginLogger;
    }

    public Page<LoginLogger> findAll(LoginLogger loginLogger, Pageable pageable) {
        if (loginLogger == null) {
            return loginLoggerRepository.findAll(pageable);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QLoginLogger qLoginLogger = QLoginLogger.loginLogger;
        if (loginLogger.getUserName() != null && !loginLogger.getUserName().isEmpty()) {
            booleanBuilder.and(qLoginLogger.userName.containsIgnoreCase(loginLogger.getUserName()));
        }
        if (loginLogger.getStatus() != null) {
            booleanBuilder.and(qLoginLogger.status.eq(loginLogger.getStatus()));
        }
        if (loginLogger.getIp() != null && !loginLogger.getIp().isEmpty()) {
            booleanBuilder.and(qLoginLogger.ip.containsIgnoreCase(loginLogger.getIp()));
        }
        if (loginLogger.getRegion() != null && !loginLogger.getRegion().isEmpty()) {
            booleanBuilder.and(qLoginLogger.region.containsIgnoreCase(loginLogger.getRegion()));
        }
        if (loginLogger.getCity() != null && !loginLogger.getCity().isEmpty()) {
            booleanBuilder.and(qLoginLogger.city.containsIgnoreCase(loginLogger.getCity()));
        }
        return loginLoggerRepository.findAll(booleanBuilder, pageable);
    }
}
