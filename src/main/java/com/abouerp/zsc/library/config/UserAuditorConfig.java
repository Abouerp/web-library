package com.abouerp.zsc.library.config;


import com.abouerp.zsc.library.utils.UserUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Abouerp
 */
public class UserAuditorConfig implements  AuditorAware<String> {


//    public Optional<Integer> getCurrentAuditor() {
//        return UserUtils.getCurrentAuditor();
//    }

    @Override
    public Optional<String> getCurrentAuditor(){
        return UserUtils.getCurrentAuditorUsername();
    }

}
