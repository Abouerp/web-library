package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.domain.Modular;
import com.abouerp.zsc.library.domain.QModular;
import com.abouerp.zsc.library.exception.ModularNotFoundException;
import com.abouerp.zsc.library.repository.ModularRepository;
import com.abouerp.zsc.library.vo.ModularVO;
import com.querydsl.core.BooleanBuilder;
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

    public Modular save(Modular modular) {
        return modularRepository.save(modular);
    }

    public void delete(Integer id) {
        modularRepository.deleteById(id);
    }

    public Modular findById(Integer id) {
        return modularRepository.findById(id).orElseThrow(ModularNotFoundException::new);
    }

    public Modular findByName(String name) {
        return modularRepository.findFirstByName(name).orElseThrow(ModularNotFoundException::new);
    }

    public Page<Modular> findAll(Pageable pageable, ModularVO modularVO) {
        if (modularVO == null) {
            return modularRepository.findAll(pageable);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QModular qModular = QModular.modular;
        if (modularVO.getName() != null && !modularVO.getName().isEmpty()) {
            booleanBuilder.and(qModular.name.containsIgnoreCase(modularVO.getName()));
        }
        return modularRepository.findAll(booleanBuilder, pageable);
    }
}
