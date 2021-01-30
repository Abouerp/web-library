package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.config.StorageProperties;
import com.abouerp.zsc.library.exception.StorageFileNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Abouerp
 */
@Log4j2
@Service
public class FileStorageService {
    private final Path rootLocation;

    public FileStorageService(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        }
        log.info("file storage path = {}", rootLocation.toString());
    }

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (file.isEmpty()) {
            throw new StorageFileNotFoundException();
        }

        if (originalFilename != null && originalFilename.contains("..")) {
            // This is a security check
            throw new StorageFileNotFoundException();
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (originalFilename != null && !originalFilename.isEmpty()) {
                digest.update(originalFilename.getBytes());
            }
            Path temp = Files.createTempFile("temp-", null);
            try (
                    InputStream in = file.getInputStream();
                    OutputStream out = Files.newOutputStream(temp)
            ) {
                int n;
                byte[] buf = new byte[8192];

                while (-1 != (n = in.read(buf, 0, buf.length))) {
                    digest.update(buf, 0, n);
                    out.write(buf, 0, n);
                }

                String md5 = String.format("%032X", new BigInteger(1, digest.digest()));
                log.info("MD5 = {}" + md5);

                Path dest = rootLocation.resolve(md5);
                if (dest.toFile().exists()) {
                    return md5;
                }
                Files.move(temp, dest);
                return md5;
            } catch (IOException e) {
                throw new StorageFileNotFoundException();
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new StorageFileNotFoundException();
        }
    }

    public Resource findByHash(String hash) throws Exception {
        try {
            Resource resource = new UrlResource(rootLocation.resolve(hash).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("File can't found---------------");
                throw new StorageFileNotFoundException();
            }
        } catch (MalformedURLException e) {
            log.info("localhost is error=====================");
            throw new StorageFileNotFoundException();
        }
    }

    public void delete(String hash) {
        FileSystemUtils.deleteRecursively(rootLocation.resolve(hash).toFile());
    }
}
