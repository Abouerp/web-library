package com.abouerp.zsc.library.dao;


import com.abouerp.zsc.library.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, QuerydslPredicateExecutor<Role> {

    Optional<Role> findFirstByName(String name);

    List<Role> findByIdIn(List<Integer> roles);

    Optional<Role> findFirstByIsDefault(Boolean isDefault);
}
