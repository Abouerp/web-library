package com.abouerp.zsc.library.repository;

import com.abouerp.zsc.library.domain.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer>, QuerydslPredicateExecutor<BookCategory> {

    BookCategory findByCode(String code);
}
