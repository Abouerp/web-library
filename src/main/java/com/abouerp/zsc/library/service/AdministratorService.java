package com.abouerp.zsc.library.service;


import com.abouerp.zsc.library.domain.user.QAdministrator;
import com.abouerp.zsc.library.repository.AdministratorRepository;
import com.abouerp.zsc.library.domain.user.Administrator;
import com.abouerp.zsc.library.vo.AdministratorVO;
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
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public void delete(Integer id) {
        administratorRepository.deleteById(id);
    }

    public Optional<Administrator> findById(Integer id) {
        return administratorRepository.findById(id);
    }

    public Optional<Administrator> findFirstByUsername(String username) {
        return administratorRepository.findFirstByUsername(username);
    }

    public Page<Administrator> findAll(AdministratorVO administrator, Pageable pageable) {
        QAdministrator qAdministrator = QAdministrator.administrator;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (administrator != null && administrator.getUsername() != null && !administrator.getUsername().isEmpty()) {
            booleanBuilder.and(qAdministrator.username.containsIgnoreCase(administrator.getUsername()));
        }
        if (administrator != null && administrator.getEmail() != null && !administrator.getEmail().isEmpty()) {
            booleanBuilder.and(qAdministrator.email.containsIgnoreCase(administrator.getEmail()));
        }
        if (administrator != null && administrator.getMobile() != null && !administrator.getMobile().isEmpty()) {
            booleanBuilder.and(qAdministrator.mobile.containsIgnoreCase(administrator.getMobile()));
        }
        if (administrator != null && administrator.getSex() != null && !administrator.getSex().isEmpty()) {
            booleanBuilder.and(qAdministrator.sex.containsIgnoreCase(administrator.getSex()));
        }
        if (administrator != null && administrator.getNickName() != null && !administrator.getNickName().isEmpty()) {
            booleanBuilder.and(qAdministrator.nickName.containsIgnoreCase(administrator.getNickName()));
        }
        if (administrator != null && administrator.getDescription() != null && !administrator.getDescription().isEmpty()) {
            booleanBuilder.and(qAdministrator.description.containsIgnoreCase(administrator.getDescription()));
        }
        return administratorRepository.findAll(booleanBuilder, pageable);
    }

    public List<Administrator> saveAll(List<Administrator> administratorList) {
        return administratorRepository.saveAll(administratorList);
    }

    public Administrator getOne(Integer id) {
        return administratorRepository.getOne(id);
    }

    public void deleteById(Integer id) {
        administratorRepository.deleteById(id);
    }

//    public List<Administrator> findByIdIn(List<Integer> ids) {
//        return administratorRepository.findByIdIn(ids);
//    }

    public boolean existsByUserName(String username) {
        Boolean flag1 = administratorRepository.existsByUsername(username);
        Boolean flag2 = administratorRepository.existsByEmail(username);
        Boolean flag3 = administratorRepository.existsByMobile(username);
        if (Boolean.FALSE.equals(flag1) && flag1.equals(flag2) && flag2.equals(flag3)) {
            return false;
        }
        return true;
    }

}
