package com.abouerp.zsc.library.repository.search;

import com.abouerp.zsc.library.domain.book.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Abouerp
 */
@Repository
public interface BookSearchRepository extends ElasticsearchRepository<Book, Integer> {

    @Transactional
    void deleteByIdIn(Collection<Integer> id);
}
