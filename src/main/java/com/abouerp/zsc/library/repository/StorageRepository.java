package com.abouerp.zsc.library.repository;

import com.abouerp.zsc.library.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findByMd5(String md5);
}
