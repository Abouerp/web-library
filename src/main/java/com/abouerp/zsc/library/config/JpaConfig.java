package com.abouerp.zsc.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Abouerp
 */
@EnableJpaRepositories(basePackages = "com.abouerp.zsc.library.dao")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new UserAuditorConfig();
    }
}
