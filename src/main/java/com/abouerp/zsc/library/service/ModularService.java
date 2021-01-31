package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.domain.Modular;
import com.abouerp.zsc.library.exception.ModularNotFoundException;
import com.abouerp.zsc.library.repository.ModularRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class ModularService {

    private final ModularRepository modularRepository;

    public ModularService(ModularRepository modularRepository) {
        this.modularRepository = modularRepository;
    }

    public Modular save(Modular modular){
        return modularRepository.save(modular);
    }

    public void delete(Integer id){
        modularRepository.deleteById(id);
    }

    public Modular findById(Integer id){
        return modularRepository.findById(id).orElseThrow(ModularNotFoundException::new);
    }

    public Page<Modular> findAll(Pageable pageable){
        return modularRepository.findAll(pageable);
    }
}
