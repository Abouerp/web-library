package com.abouerp.zsc.library.repository;

import com.abouerp.zsc.library.domain.Modular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
public interface ModularRepository extends JpaRepository<Modular,Integer> {

    Optional<Modular> findFirstByName(String name);
}
