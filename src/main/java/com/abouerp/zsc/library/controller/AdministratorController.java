package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.user.Administrator;
import com.abouerp.zsc.library.domain.user.Role;
import com.abouerp.zsc.library.dto.AdministratorDTO;
import com.abouerp.zsc.library.exception.PasswordNotMatchException;
import com.abouerp.zsc.library.exception.UserNotFoundException;
import com.abouerp.zsc.library.mapper.AdministratorMapper;
import com.abouerp.zsc.library.security.UserPrincipal;
import com.abouerp.zsc.library.service.AdministratorService;
import com.abouerp.zsc.library.service.RoleService;
import com.abouerp.zsc.library.vo.AdministratorVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final AdministratorService administratorService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdministratorController(AdministratorService administratorService,
                                   RoleService roleService,
                                   PasswordEncoder passwordEncoder) {
        this.administratorService = administratorService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    private static Administrator update(Administrator administrator, Optional<AdministratorVO> administratorVO) {
        administratorVO.map(AdministratorVO::getMobile).ifPresent(administrator::setMobile);
        administratorVO.map(AdministratorVO::getEmail).ifPresent(administrator::setEmail);
        administratorVO.map(AdministratorVO::getSex).ifPresent(administrator::setSex);
        administratorVO.map(AdministratorVO::getMd5).ifPresent(administrator::setMd5);
        administratorVO.map(AdministratorVO::getEnabled).ifPresent(administrator::setEnabled);
        return administrator;
    }

    /**
     * 防止跨站请求伪造
     */
    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(@AuthenticationPrincipal Object object, CsrfToken csrfToken) {
        Map<String, Object> map = new HashMap<>(2);
        if (object instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) object;
            Administrator administrator = administratorService.getOne(userPrincipal.getId());
            map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        } else {
            map.put("user", new AdministratorDTO());
        }
        map.put("_csrf", csrfToken);
        return ResultBean.ok(map);
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('USER_READ')")
    public ResultBean<Page<AdministratorDTO>> findAll(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
            AdministratorVO administratorVO) {
        return ResultBean.ok(administratorService.findAll(administratorVO, pageable).map(AdministratorMapper.INSTANCE::toDTO));
    }

    //    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public ResultBean<AdministratorDTO> save(@RequestBody AdministratorVO adminVO) {
        Administrator administrator = administratorService.findFirstByUsername(adminVO.getUsername()).orElse(null);
        if (administrator != null) {
            return ResultBean.of(200, "User id Exist");
        }
        Set<Role> roles = roleService.findByIdIn(adminVO.getRole()).stream().collect(Collectors.toSet());
        adminVO.setPassword(passwordEncoder.encode(adminVO.getPassword()));
        administrator = AdministratorMapper.INSTANCE.toAdmin(adminVO);
        administrator.setRoles(roles);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("principal.id == #id or hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> update(@PathVariable Integer id, @RequestBody Optional<AdministratorVO> adminVO) {
        Administrator admin = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        List<Integer> roleIds = adminVO.map(AdministratorVO::getRole).get();
        if (adminVO != null && roleIds.size() != 0) {
            admin.setRoles(roleService.findByIdIn(roleIds).stream().collect(Collectors.toSet()));
        }
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(admin, adminVO))));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResultBean<Object> delete(@PathVariable Integer id) {
        administratorService.deleteById(id);
        return ResultBean.ok();
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("principal.id == #id or hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> updatePassword(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            String srcPassword,
            String password
    ) {
        Administrator administrator = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
            throw new PasswordNotMatchException();
        }
        administrator.setPassword(passwordEncoder.encode(password));
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @GetMapping("/{id}")
    public ResultBean<AdministratorDTO> findById(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administrator));
    }
}
