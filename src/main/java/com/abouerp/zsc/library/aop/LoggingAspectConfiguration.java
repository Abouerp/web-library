package com.abouerp.zsc.library.aop;

import com.abouerp.zsc.library.service.OperatorLoggerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abouerp
 */
@Configuration
public class LoggingAspectConfiguration {

    @Bean
    public LoggingAspect loggingAspect(OperatorLoggerService operatorLoggerService){
        return new LoggingAspect(operatorLoggerService);
    }
}
