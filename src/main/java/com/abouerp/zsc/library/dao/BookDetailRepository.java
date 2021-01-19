package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.BookDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Integer>, QuerydslPredicateExecutor<BookDetail> {

    @Query(value = "select * from BookDetail where book_id = ?1 order by id desc limit 1",
            nativeQuery = true)
    BookDetail findLastBookDetailByBookId(Integer id);

//    Page<BookDetail>
}
