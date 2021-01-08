package com.abouerp.zsc.library.dao;

import com.abouerp.zsc.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Abouerp
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {

    @Transactional
    void deleteByIdIn(Collection<Integer> id);

    /**
     * 找到类别下的最后一本书，用于生成book的code
     * @param id 类别的id
     * @return 返回该类别的最后一本的book
     */
    @Query(value = "select * from Book where bookCategory_id = ?1 order by id desc limit 1", nativeQuery = true)
    Book findBookByBookCategoryId(Integer id);

}
