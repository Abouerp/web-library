package com.abouerp.zsc.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Abouerp
 */
@EnableJpaRepositories(basePackages = "com.abouerp.zsc.library.dao")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.abouerp.zsc.library.dao.search")
public class DatabaseConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new UserAuditorConfig();
    }
}
