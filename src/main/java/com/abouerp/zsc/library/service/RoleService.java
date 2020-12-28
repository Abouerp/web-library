package com.abouerp.zsc.library.service;


import com.abouerp.zsc.library.dao.RoleRepository;
import com.abouerp.zsc.library.domain.QRole;
import com.abouerp.zsc.library.domain.Role;
import com.abouerp.zsc.library.vo.RoleVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    public List<Role> findByIdIn(List<Integer> ids) {
        return roleRepository.findByIdIn(ids);
    }

    public Page<Role> findAll(RoleVO roleVO, Pageable pageable) {
        if (roleVO == null) {
            return roleRepository.findAll(pageable);
        }
        QRole qRole = QRole.role;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (roleVO.getName() != null && !roleVO.getName().isEmpty()) {
            booleanBuilder.and(qRole.name.containsIgnoreCase(roleVO.getName()));
        }
        return roleRepository.findAll(booleanBuilder, pageable);
    }
}
