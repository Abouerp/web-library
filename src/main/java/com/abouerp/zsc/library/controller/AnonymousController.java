package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookDetail;
import com.abouerp.zsc.library.domain.user.Administrator;
import com.abouerp.zsc.library.domain.user.Role;
import com.abouerp.zsc.library.mapper.AdministratorMapper;
import com.abouerp.zsc.library.mapper.BookDetailMapper;
import com.abouerp.zsc.library.repository.BookRepository;
import com.abouerp.zsc.library.repository.search.BookSearchRepository;
import com.abouerp.zsc.library.service.AdministratorService;
import com.abouerp.zsc.library.service.BookDetailService;
import com.abouerp.zsc.library.service.ProblemManageService;
import com.abouerp.zsc.library.service.RoleService;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/anonymous")
public class AnonymousController {

    private final ProblemManageService problemManageService;
    private final BookRepository bookRepository;
    private final BookDetailService bookDetailService;
    private final BookSearchRepository bookSearchRepository;
    private final AdministratorService administratorService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AnonymousController(ProblemManageService problemManageService,
                               BookRepository bookRepository,
                               BookSearchRepository bookSearchRepository,
                               BookDetailService bookDetailService,
                               AdministratorService administratorService,
                               RoleService roleService,
                               PasswordEncoder passwordEncoder) {
        this.problemManageService = problemManageService;
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
        this.bookDetailService = bookDetailService;
        this.administratorService = administratorService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sync")
    public ResultBean sync() {
        List<Book> list = bookRepository.findAll();
        bookSearchRepository.deleteAll();
        bookSearchRepository.saveAll(list);
        return ResultBean.ok();
    }

    @GetMapping("/problem")
    public ResultBean findAllProblem(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        ProblemManageVO problemManageVO = new ProblemManageVO();
        problemManageVO.setShow(true);
        return ResultBean.ok(problemManageService.findAll(pageable, problemManageVO));
    }

    /**
     * 获取图书的详细信息，包括馆藏信息
     *
     * @param id 图书的id
     */
    @GetMapping("/book/{id}")
    public ResultBean findBookAllMessageById(@PathVariable Integer id) {
        List<BookDetail> bookDetails = bookDetailService.findByBookId(id);
        Map<String, Object> map = new HashMap<>();
        if (bookDetails.size() != 0) {
            map.put("book", bookDetails.get(0).getBook());
            map.put("detail", bookDetails.stream().map(BookDetailMapper.INSTANCE::toDTO).collect(Collectors.toList()));
        } else {
            map.put("book", bookRepository.findById(id));
        }
        return ResultBean.ok(map);
    }

    @GetMapping("/user/register")
    public ResultBean register(@RequestParam String username, @RequestParam String password) {
        Administrator administrator = administratorService.findFirstByUsername(username).orElse(null);
        if (administrator != null) {
            return ResultBean.of(200, "User is Exist");
        }
        Role role = roleService.findFirstByIsDefault(true);
        if (role == null) {
            return ResultBean.of(200, "Can't find default role");
        }
        administrator.setUsername(username).setPassword(passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        administrator.setRoles(roles);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }
}
