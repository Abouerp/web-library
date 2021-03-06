package com.abouerp.zsc.library.security;


import com.abouerp.zsc.library.repository.AdministratorRepository;
import com.abouerp.zsc.library.domain.user.Administrator;
import com.abouerp.zsc.library.exception.UnauthorizedException;
import com.abouerp.zsc.library.mapper.AdministratorMapper;
import com.abouerp.zsc.library.service.LoginLoggerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final LoginLoggerService loginLoggerService;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    private final String regx = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";

    public UserDetailsServiceImpl(AdministratorRepository administratorRepository,
                                  LoginLoggerService loginLoggerService,
                                  PasswordEncoder passwordEncoder,
                                  HttpServletRequest httpServletRequest) {
        this.administratorRepository = administratorRepository;
        this.loginLoggerService = loginLoggerService;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        Administrator administrator;
        if (Boolean.TRUE.equals(Pattern.compile(regx).matcher(s).matches())) {
            administrator = administratorRepository.findFirstByMobile(s).orElse(null);
        } else if (s.contains("@")) {
            administrator = administratorRepository.findFirstByEmail(s).orElse(null);
        } else {
            administrator = administratorRepository.findFirstByUsername(s).orElse(null);
        }
        if (administrator == null) {
            loginLoggerService.fail(s, "User don't exist");
            throw new UnauthorizedException();
        }
        String password = httpServletRequest.getParameter("password");
        if (Boolean.FALSE.equals(passwordEncoder.matches(password, administrator.getPassword()))) {
            loginLoggerService.fail(s, "Password Error");
            throw new UnauthorizedException();
        }

        loginLoggerService.success(s);
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
