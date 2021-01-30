package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.exception.BadRequestException;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Abouerp
 */
@Slf4j
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final BookRepository bookRepository;
    private final BookSearchRepository bookSearchRepository;
    private final RestHighLevelClient restHighLevelClient;
    private static final String INDEX = "library_books";

    public SearchController(BookRepository bookRepository,
                            BookSearchRepository bookSearchRepository,
                            RestHighLevelClient restHighLevelClient) {
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
        this.restHighLevelClient = restHighLevelClient;
    }

    @GetMapping("/sync")
    public ResultBean tst() {
        List<Book> list = bookRepository.findAll();
        bookSearchRepository.deleteAll();
        bookSearchRepository.saveAll(list);
        return ResultBean.ok();
    }

    @GetMapping("/high")
    public ResultBean search(@RequestParam String keyword, @PageableDefault Pageable pageable) {
        //创建请求
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageable.getPageNumber());
        sourceBuilder.size(pageable.getPageSize());

        //匹配
        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "description", "author", "publisher"));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("name")
                .field("description")
                .field("author")
                .field("publisher")
                .preTags("<span style='color:red;'>")
                .postTags("</span>")
                .requireFieldMatch(false); //是否需要多个字段高亮显示 默认是false
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("搜索查询出错-------------------");
            throw new BadRequestException();
        }

        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            HighlightField description = highlightFields.get("description");
            HighlightField author = highlightFields.get("author");
            HighlightField publisher = highlightFields.get("publisher");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段换为高亮字段即可
            if (name != null) {
                Text[] fragments = name.fragments();
                String n_name = "";
                for (Text text : fragments) {
                    n_name += text;
                }
                sourceAsMap.put("name", n_name);//高亮字段替换原来内容
                list.add(sourceAsMap);
                continue;
            }
            if (author != null) {
                Text[] fragments = author.getFragments();
                String n_author = "";
                for (Text text : fragments) {
                    n_author += text;
                }
                sourceAsMap.put("author", n_author);
                list.add(sourceAsMap);
                continue;
            }
            if (publisher != null) {
                Text[] fragments = publisher.getFragments();
                String n_publisher = "";
                for (Text text : fragments) {
                    n_publisher += text;
                }
                sourceAsMap.put("publisher", n_publisher);
                list.add(sourceAsMap);
                continue;
            }
            if (description != null) {
                Text[] fragments = description.getFragments();
                String n_description = "";
                for (Text text : fragments) {
                    n_description += text;
                }
                sourceAsMap.put("description", n_description);
                list.add(sourceAsMap);
            }
        }
        return ResultBean.ok(list);
    }
}