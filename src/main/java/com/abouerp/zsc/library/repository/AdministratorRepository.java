package com.abouerp.zsc.library.repository;


import com.abouerp.zsc.library.domain.Administrator;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>, QuerydslPredicateExecutor<Administrator> {
    @EntityGraph(attributePaths = "roles.authorities")
    Optional<Administrator> findFirstByUsername(String username);

    @EntityGraph(attributePaths = "roles.authorities")
    Optional<Administrator> findFirstByEmail(String email);

    @EntityGraph(attributePaths = "roles.authorities")
    Optional<Administrator> findFirstByMobile(String mobile);

}
