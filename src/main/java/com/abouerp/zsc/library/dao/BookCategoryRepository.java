package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer>, QuerydslPredicateExecutor<BookCategory> {
}
