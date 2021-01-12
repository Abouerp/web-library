package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Integer>, QuerydslPredicateExecutor<BookDetail> {
}
