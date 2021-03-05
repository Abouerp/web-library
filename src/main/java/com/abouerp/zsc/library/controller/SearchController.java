//package com.abouerp.zsc.library.controller;
//
//import com.abouerp.zsc.library.bean.ResultBean;
//import com.abouerp.zsc.library.domain.book.Book;
//import com.abouerp.zsc.library.exception.BadRequestException;
//import com.abouerp.zsc.library.repository.search.BookSearchRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Abouerp
// */
//@Slf4j
//@RestController
//@RequestMapping("/api/search")
//public class SearchController {
//
//
//    private final RestHighLevelClient restHighLevelClient;
//    private final BookSearchRepository bookSearchRepository;
//    private static final String INDEX = "library_books";
//
//    public SearchController(RestHighLevelClient restHighLevelClient,
//                            BookSearchRepository bookSearchRepository) {
//        this.restHighLevelClient = restHighLevelClient;
//        this.bookSearchRepository = bookSearchRepository;
//    }
//
//    /**
//     * 全局搜索
//     * @param keyword
//     */
//    @GetMapping("/high")
//    public ResultBean search(@RequestParam String keyword, @PageableDefault Pageable pageable) {
//        //创建请求
//        SearchRequest searchRequest = new SearchRequest(INDEX);
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        //分页,利用pageImpl进行分页
//        sourceBuilder.from(pageable.getPageNumber() * pageable.getPageSize());
//        sourceBuilder.size(pageable.getPageSize());
//
//        //匹配
//        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "description", "author", "publisher"));
//
//        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder()
//                .field("name")
//                .field("description")
//                .field("author")
//                .field("publisher")
//                .preTags("<em data=\"")
//                .postTags("\"></em>")
//                .numOfFragments(0)
//                .requireFieldMatch(true); //是否需要多个字段高亮显示 默认是false
//        sourceBuilder.highlighter(highlightBuilder);
//
//        //执行搜索
//        searchRequest.source(sourceBuilder);
//        SearchResponse searchResponse;
//        try {
//            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            log.info("搜索查询出错-------------------");
//            throw new BadRequestException();
//        }
//
//        //解析结果
//        ArrayList<Map<String, Object>> list = new ArrayList<>();
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            //解析高亮字段
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            HighlightField name = highlightFields.get("name");
//            HighlightField description = highlightFields.get("description");
//            HighlightField author = highlightFields.get("author");
//            HighlightField publisher = highlightFields.get("publisher");
////            log.info(" score = {}",hit.getScore());
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            //解析高亮字段，将原来的字段换为高亮字段即可
//            if (name != null) {
//                Text[] fragments = name.fragments();
//                String n_name = "";
//                for (Text text : fragments) {
//                    n_name += text;
//                }
////                log.info("name = {}", sourceAsMap.get("name"));
//                sourceAsMap.put("name", n_name);//高亮字段替换原来内容
//                list.add(sourceAsMap);
//                continue;
//            }
//            if (author != null) {
//                Text[] fragments = author.getFragments();
//                String n_author = "";
//                for (Text text : fragments) {
//                    n_author += text;
//                }
//                sourceAsMap.put("author", n_author);
//                list.add(sourceAsMap);
//                continue;
//            }
//            if (publisher != null) {
//                Text[] fragments = publisher.getFragments();
//                String n_publisher = "";
//                for (Text text : fragments) {
//                    n_publisher += text;
//                }
//                sourceAsMap.put("publisher", n_publisher);
//                list.add(sourceAsMap);
//                continue;
//            }
//            if (description != null) {
//                Text[] fragments = description.getFragments();
//                String n_description = "";
//                for (Text text : fragments) {
//                    n_description += text;
//                }
//                sourceAsMap.put("description", n_description);
//                list.add(sourceAsMap);
//            }
//        }
//        return ResultBean.ok(new PageImpl(list, pageable, searchResponse.getHits().getTotalHits()));
//
//    }
//
//    /**
//     * 联想搜索
//     * @param keyword
//     */
//    @GetMapping("/tip")
//    public ResultBean getTip(@RequestParam String keyword, @PageableDefault Pageable pageable) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withIndices(INDEX)
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("name", keyword))
//                )
//                .withHighlightFields(new HighlightBuilder.Field(keyword))
//                .withPageable(pageable)
//                .build();
//        return ResultBean.ok(bookSearchRepository.search(searchQuery).getContent().stream().map(Book::getName));
//    }
//}
