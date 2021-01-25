package com.abouerp.zsc.library.repository;

import com.abouerp.zsc.library.domain.logger.LoginLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * @author Abouerp
 */
@Repository
public interface LoginLoggerRepository extends JpaRepository<LoginLogger, Long>, QuerydslPredicateExecutor<LoginLogger> {
}
