package com.abouerp.zsc.library.security;


import com.abouerp.zsc.library.dao.AdministratorRepository;
import com.abouerp.zsc.library.domain.Administrator;
import com.abouerp.zsc.library.exception.UnauthorizedException;
import com.abouerp.zsc.library.mapper.AdministratorMapper;
import com.abouerp.zsc.library.service.LoginLoggerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final LoginLoggerService loginLoggerService;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;

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
        Administrator administrator = administratorRepository.findFirstByUsername(s).orElse(null);
        if (administrator == null) {
            loginLoggerService.fail(s, "User don't exist");
            throw new UnauthorizedException();
//            return null;
        }
        String password = httpServletRequest.getParameter("password");
        if (Boolean.FALSE.equals(passwordEncoder.matches(password, administrator.getPassword()))) {
            loginLoggerService.fail(s, "Password Error");
            throw new UnauthorizedException();
//            return null;
        }

        loginLoggerService.success(s);
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
