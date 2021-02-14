package com.abouerp.zsc.library.controller;


import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.user.Role;
import com.abouerp.zsc.library.exception.RoleNotFoundException;
import com.abouerp.zsc.library.mapper.RoleMapper;
import com.abouerp.zsc.library.service.RoleService;
import com.abouerp.zsc.library.vo.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    private static Role updateInfo(Role role, RoleVO roleVO) {
        if (roleVO != null && roleVO.getDescription() != null) {
            role.setDescription(roleVO.getDescription());
        }
        if (roleVO != null && roleVO.getName() != null) {
            role.setName(roleVO.getName());
        }
        if (roleVO != null && roleVO.getAuthorities() != null && !roleVO.getAuthorities().isEmpty()) {
            role.setAuthorities(roleVO.getAuthorities());
        }
        if (roleVO != null && roleVO.getIsDefault() != null) {
            role.setIsDefault(roleVO.getIsDefault());
        }
        return role;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResultBean<Role> save(@RequestBody RoleVO roleVO) {
        return ResultBean.ok(roleService.save(RoleMapper.INSTANCE.toRole(roleVO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResultBean<Object> delete(@PathVariable Integer id) {
        Role role = roleService.findById(id).orElseThrow(RoleNotFoundException::new);
        if (role.getIsDefault() == true) {
            return ResultBean.of(400, "DEFAULT ROLE CAN'T BE DELETE");
        }
        roleService.deleteById(id);
        return ResultBean.ok();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResultBean<Role> update(@PathVariable Integer id, @RequestBody RoleVO roleVO) {
        Role role = roleService.findById(id).orElseThrow(RoleNotFoundException::new);
        return ResultBean.ok(roleService.save(updateInfo(role, roleVO)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResultBean<Page<Role>> findAll(
            @PageableDefault Pageable pageable,
            RoleVO roleVO
    ) {
        return ResultBean.ok(roleService.findAll(roleVO, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResultBean findById(@PathVariable Integer id){
        return ResultBean.ok(roleService.findById(id).orElseThrow(RoleNotFoundException::new));
    }
}
