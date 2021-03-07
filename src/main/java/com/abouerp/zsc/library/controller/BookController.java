package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookCategory;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.dto.ReptileBookDTO;
import com.abouerp.zsc.library.exception.BookCategoryNotFoundException;
import com.abouerp.zsc.library.exception.BookNotFoundException;
import com.abouerp.zsc.library.mapper.BookMapper;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.service.BookCategoryService;
import com.abouerp.zsc.library.service.BookService;
import com.abouerp.zsc.library.service.FileStorageService;
import com.abouerp.zsc.library.utils.JsonUtils;
import com.abouerp.zsc.library.utils.RandomDateUtils;
import com.abouerp.zsc.library.utils.SecurityUtils;
import com.abouerp.zsc.library.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final BookCategoryService bookCategoryService;
    private final FileStorageService fileStorageService;
    private final BookSearchRepository bookSearchRepository;
    private String url = "https://www.szlib.org.cn/api/opacservice/getQueryResult";
    private static final String INDEX = "library_books";

    public BookController(BookService bookService, BookCategoryService bookCategoryService,
                          FileStorageService fileStorageService,
                          BookSearchRepository bookSearchRepository) {
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
        this.fileStorageService = fileStorageService;
        this.bookSearchRepository = bookSearchRepository;
    }

    private static Book update(Book book, Optional<BookVO> bookVO) {
        bookVO.map(BookVO::getName).ifPresent(book::setName);
        bookVO.map(BookVO::getAuthor).ifPresent(book::setAuthor);
        bookVO.map(BookVO::getDescription).ifPresent(book::setDescription);
        bookVO.map(BookVO::getIsbn).ifPresent(book::setIsbn);
        bookVO.map(BookVO::getPrice).ifPresent(book::setPrice);
        bookVO.map(BookVO::getPublicationTime).ifPresent(book::setPublicationTime);
        bookVO.map(BookVO::getPublisher).ifPresent(book::setPublisher);
        return book;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK_CREATE')")
    public ResultBean save(@RequestBody BookVO bookVO) {
        if (bookService.findByIsbn(bookVO.getIsbn()) != null) {
            return ResultBean.of(200, "ISBN is exist");
        }
        BookCategory bookCategory = bookCategoryService.findById(bookVO.getBookCategoryId())
                .orElseThrow(BookCategoryNotFoundException::new);
        Book lastBook = bookService.findLastBookByBookCategoryId(bookCategory.getId());
        Book book = BookMapper.INSTANCE.toBook(bookVO);
        book.setCode(getCode(lastBook, bookCategory.getCode()));
        book.setBookCategory(bookCategory);
        return ResultBean.ok(bookService.save(book));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_UPDATE')")
    public ResultBean<BookDTO> update(@PathVariable Integer id, @RequestBody Optional<BookVO> bookVO) {
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        Integer bookCategoryId = bookVO.get().getBookCategoryId();
        bookVO.map(BookVO::getBookCategoryId).ifPresent(it ->
                book.setBookCategory(bookCategoryService.findById(bookCategoryId).get())
        );
        return ResultBean.ok(bookService.save(update(book, bookVO)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BOOK_READ')")
    public ResultBean<Page<BookDTO>> findAll(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
            BookVO bookVO) {
        return ResultBean.ok(bookService.findAll(bookVO, pageable).map(BookMapper.INSTANCE::toDTO));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    public ResultBean delete(@RequestBody Set<Integer> ids) {
        bookService.delete(ids);
        return ResultBean.ok();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BOOK_READ')")
    public ResultBean<BookDTO> findById(@PathVariable Integer id) {
        Book book = bookService.findById(id).orElseThrow(BookNotFoundException::new);
        return ResultBean.ok(BookMapper.INSTANCE.toDTO(book));
    }

    /**
     * 解析excel文件中的图书数据返给前端
     */
    @PostMapping("/excel")
    public ResultBean analysisExcel(@RequestParam MultipartFile file) {
        return ResultBean.ok(fileStorageService.analysisExcel(file));
    }

    /**
     * 批量保存图书
     *
     * @param id   类别id
     * @param list 图书vo
     */
    @PostMapping("/batch/{id}")
    public ResultBean saveAll(@PathVariable Integer id, @RequestBody List<BookVO> list) {
        BookCategory bookCategory = bookCategoryService.findById(id).orElseThrow(BookCategoryNotFoundException::new);
        Book preBook = bookService.findLastBookByBookCategoryId(id);
        Integer code = Integer.parseInt(getCode(preBook, bookCategory.getCode()).substring(4));
        List<Book> bookList = list.stream().map(BookMapper.INSTANCE::toBook).collect(Collectors.toList());
        for (Book book : bookList) {
            book.setBookCategory(bookCategory);
            book.setCode(String.format(bookCategory.getCode() + "%04d", code++));
            bookService.save(book);
        }
        return ResultBean.ok();
    }

    /**
     * 获取一个图书类别下新增一本书应设置的code字段
     *
     * @param lastBook     该类别下最后一本书
     * @param categoryCode 类别的编码
     */
    private String getCode(Book lastBook, String categoryCode) {
        if (lastBook == null) {
            return String.format(categoryCode + "0001");
        } else {
            Integer code = Integer.parseInt(lastBook.getCode().substring(4));
            return String.format(categoryCode + "%04d", code + 1);
        }
    }

    /**
     * 爬取数据
     * @param keyword  爬取关键字
     */
    @GetMapping("/reptile")
    public ResultBean reptileBook(@RequestParam String keyword,
                                  @RequestParam(defaultValue = "1") String page,
                                  @RequestParam(defaultValue = "50") String size) {
        //解决内存溢出问题
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024_0000)).build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("v_index","title")
                .queryParam("library","all")
                .queryParam("v_tablearray","bibliosm,serbibm,apabibibm,mmbibm,")
                .queryParam("sortfield","ptitle")
                .queryParam("sorttype","desc")
                .queryParam("pageNum",size) //个数
                .queryParam("v_page",page)   //页数
                .queryParam("v_value",keyword);

        //爬取数据
        ReptileBookDTO.Message message = WebClient
                .builder()
                .exchangeStrategies(exchangeStrategies)
                .build()
                .get()
                .uri(uriComponentsBuilder.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .map(it -> JsonUtils.readValue(it, ReptileBookDTO.class))
                .filter(it -> it.getData() != null)
                .map(ReptileBookDTO::getData).block();

        //插入数据库
        //设置图书类别，在es根据关键字查询，若没有，则在数据库中查找，若数据库没有，创建一个
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(INDEX)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", keyword)))
                .withPageable(PageRequest.of(0, 10))
                .build();
        List<Book> search = bookSearchRepository.search(searchQuery).getContent();
        BookCategory bookCategory;
        if (search.size() != 0) {
            bookCategory = search.get(0).getBookCategory();
        } else {
            List<BookCategory> bookCategories = bookCategoryService.findAll(null, PageRequest.of(0, 10)).getContent();
            if (bookCategories.size()!=0){
                bookCategory = bookCategories.get(0);
            }else {
                String s = UUID.randomUUID().toString().substring(0,2)+"01";
                bookCategory = new BookCategory()
                        .setName(keyword)
                        .setCode(s)
                        .setCreateTime(Instant.now())
                        .setCreateBy(SecurityUtils.getCurrentUserLogin());
                bookCategory = bookCategoryService.save(bookCategory);
            }
        }
        Book preBook = bookService.findLastBookByBookCategoryId(bookCategory.getId());
        Integer code = Integer.parseInt(getCode(preBook, bookCategory.getCode()).substring(4));

        List<ReptileBookDTO.Result> docses = message.getDocs();
        Random random = new Random();
        List<Book> list = new ArrayList<>();
        for (ReptileBookDTO.Result docs: docses){
            Book book = new Book()
                    .setName(docs.getTitle())
                    .setCode(String.format(bookCategory.getCode() + "%04d", code++))
                    .setIsbn(docs.getIsbn())
                    .setAuthor(docs.getAuthor())
                    .setPublisher(docs.getPublisher())
                    .setDescription(docs.getU_abstract())
                    .setPrice(random.nextDouble()*100)
                    .setPublicationTime(RandomDateUtils.dateTime())
                    .setBookCategory(bookCategory)
                    .setCreateTime(Instant.now())
                    .setCreateBy(SecurityUtils.getCurrentUserLogin());
            bookService.save(book);
            list.add(book);
        }
        return ResultBean.ok(list);
    }
}
