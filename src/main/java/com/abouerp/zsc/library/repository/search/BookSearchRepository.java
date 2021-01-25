package com.abouerp.zsc.library.repository.search;

import com.abouerp.zsc.library.domain.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Abouerp
 */
public interface BookSearchRepository extends ElasticsearchRepository<Book, Integer> {
}
