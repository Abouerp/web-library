package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.logger.OperatorLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface OperatorLoggerRepository extends JpaRepository<OperatorLogger,Long>, QuerydslPredicateExecutor<OperatorLogger> {
}
