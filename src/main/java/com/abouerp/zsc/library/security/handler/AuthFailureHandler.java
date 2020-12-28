package com.abouerp.zsc.library.security.handler;

import com.abouerp.zsc.library.exception.ClientErrorException;
import com.abouerp.zsc.library.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证失败 处理
 *
 * @author Abouerp
 */
@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public AuthFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ClientErrorException clientErrorException;
        if (exception instanceof InternalAuthenticationServiceException && exception.getCause() instanceof ClientErrorException) {
            clientErrorException = (ClientErrorException) exception.getCause();
        } else {
            clientErrorException = new UnauthorizedException();
        }
        response.setStatus(clientErrorException.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(clientErrorException.getResultBean()));
    }
}
