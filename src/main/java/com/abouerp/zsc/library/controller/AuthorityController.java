package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.user.Authority;
import com.abouerp.zsc.library.domain.user.Role;
import com.abouerp.zsc.library.exception.RoleNotFoundException;
import com.abouerp.zsc.library.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/authority")
public class AuthorityController {

    private final RoleService roleService;

    public AuthorityController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    public ResultBean getAll() {
        return ResultBean.ok(Authority.mappings);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResultBean<Role> updateRoleAuthority(@PathVariable Integer id, @RequestBody Set<Authority> authorities) {
        Role role = roleService.findById(id).orElseThrow(RoleNotFoundException::new);
        role.setAuthorities(authorities);
        return ResultBean.ok(roleService.save(role));
    }
}
