package com.abouerp.zsc.library.repository;

import com.abouerp.zsc.library.domain.Modular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * @author Abouerp
 */
public interface ModularRepository extends JpaRepository<Modular,Integer>, QuerydslPredicateExecutor<Modular> {

    Optional<Modular> findFirstByName(String name);
}
