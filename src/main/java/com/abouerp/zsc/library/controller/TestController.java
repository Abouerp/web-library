package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.config.RabbitMqConfiguration;
import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookCategory;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.utils.JsonUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookSearchRepository bookSearchRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    private static final String INDEX = "library_books";

    @GetMapping("/sync")
    public ResultBean tst(){
        List<Book> list = bookRepository.findAll();
        bookSearchRepository.deleteAll();
        bookSearchRepository.saveAll(list);
        return ResultBean.ok();
    }

    @GetMapping("/sss")
    public ResultBean testT(String name, @PageableDefault Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices(INDEX)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", name))
//                        .mustNot(QueryBuilders.matchQuery("description",name))
                )
                .withHighlightFields(new HighlightBuilder.Field(name)
                        .preTags("<span style='color:red'>")
                        .postTags("</span>"))
                .withPageable(pageable)
                .build();
        return ResultBean.ok(bookSearchRepository.search(nativeSearchQuery));
    }

    @GetMapping("/high")
    public ResultBean te22stT(String keyword, @PageableDefault Pageable pageable) throws IOException {
        //创建请求
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageable.getPageNumber());
        sourceBuilder.size(pageable.getPageSize());

        //匹配
        sourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("name", keyword)));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("name")
                .requireFieldMatch(false)
                .preTags("<span style='color:red;'>")
                .postTags("</span>")
                .requireFieldMatch(false); //是否需要多个字段高亮显示
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        //解析结果
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit:searchResponse.getHits().getHits()){
            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String,Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段换为高亮字段即可
            if (name!=null){
                Text[] fragments = name.fragments();
                String n_name = "";
                for (Text text:fragments){
                    n_name += text;
                }
                sourceAsMap.put("name",n_name);//高亮字段替换原来内容
            }
            list.add(sourceAsMap);
        }
        return ResultBean.ok(list);
    }
}
