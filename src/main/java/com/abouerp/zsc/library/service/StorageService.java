package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.repository.StorageRepository;
import com.abouerp.zsc.library.domain.Storage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Abouerp
 */
@Service
@Log4j2
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional
    public String save(String md5, MultipartFile file) {
        Storage storage = storageRepository.findByMd5(md5).orElse(new Storage());
        storage = storage
                .setMd5(md5)
                .setContentType(file.getContentType())
                .setOriginalFilename(file.getOriginalFilename());
        log.info("stroage = {}", storage);
        storageRepository.save(storage);
        return null;
    }

    public Storage findByMd5(String id) {
        Storage storage = storageRepository.findByMd5(id).orElse(null);
        if (storage == null) {
            return null;
        } else {
            return storage;
        }


    }

}
