package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer>, QuerydslPredicateExecutor<BookCategory> {

    BookCategory findByCode(String code);
}
