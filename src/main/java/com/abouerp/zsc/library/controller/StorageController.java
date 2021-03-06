package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.Storage;
import com.abouerp.zsc.library.service.FileStorageService;
import com.abouerp.zsc.library.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Abouerp
 */
@Slf4j
@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final StorageService storageService;
    private final FileStorageService fileStorageService;

    public StorageController(
            StorageService storageService,
            FileStorageService fileStorageService) {
        this.storageService = storageService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResultBean<String> save(@RequestParam MultipartFile file) {
        String md5 = fileStorageService.upload(file);
        storageService.save(md5, file);
        return new ResultBean<>(md5);
    }

    @GetMapping(value = "/preview/{id}")
    public ResponseEntity<Resource> preview(@PathVariable String id) throws Exception {
        Storage storage = storageService.findByMd5(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, storage.getContentType())
                .body(fileStorageService.findByHash(storage.getMd5()));
    }

    /**
     * 获取book批量插入的excel模板
     */
    @GetMapping("/download/book-model")
    public ResponseEntity<Resource> getModel() {
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", "book_model.xls")
        ).body(new ClassPathResource("excel/book_model.xls"));
    }
}
