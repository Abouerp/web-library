package com.abouerp.zsc.library.security;


import com.abouerp.zsc.library.dao.AdministratorRepository;
import com.abouerp.zsc.library.domain.Administrator;
import com.abouerp.zsc.library.exception.UnauthorizedException;
import com.abouerp.zsc.library.mapper.AdministratorMapper;
import com.abouerp.zsc.library.service.LoginLoggerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final LoginLoggerService loginLoggerService;

    public UserDetailsServiceImpl(AdministratorRepository administratorRepository,
                                  LoginLoggerService loginLoggerService) {
        this.administratorRepository = administratorRepository;
        this.loginLoggerService = loginLoggerService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findFirstByUsername(s).orElseThrow(null);
        if (administrator == null) {
            loginLoggerService.fail(s);
            throw new UnauthorizedException();
        }
        loginLoggerService.success(s);
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
