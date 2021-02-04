package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.config.StorageProperties;
import com.abouerp.zsc.library.exception.BadRequestException;
import com.abouerp.zsc.library.exception.ExcelErrorException;
import com.abouerp.zsc.library.exception.StorageFileNotFoundException;
import com.abouerp.zsc.library.vo.BookVO;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.util.ArrayList;
import java.util.List;

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
                    //拼接成url，然后查看该文件夹下是否有这个文件
                    return md5;
                }
                //保存文件
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

    public List<BookVO> analysisExcel(MultipartFile file) {
        if (file == null) {
            throw new BadRequestException();
        }
        try {
            InputStream inputStream = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<BookVO> list = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }
                BookVO bookVO = new BookVO();
                if (row.getCell(0).getStringCellValue() != null) {
                    bookVO.setName(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1).getStringCellValue() != null) {
                    bookVO.setIsbn(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2).getStringCellValue() != null) {
                    bookVO.setAuthor(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3).getStringCellValue() != null) {
                    bookVO.setPublisher(row.getCell(3).getStringCellValue());
                }
                if (row.getCell(4).getStringCellValue() != null) {
                    bookVO.setDescription(row.getCell(4).getStringCellValue());
                }
                if (row.getCell(5).getStringCellValue() != null) {
                    bookVO.setPrice(Double.parseDouble(row.getCell(5).getStringCellValue()));
                }
                if (row.getCell(6).getStringCellValue() != null) {
                    bookVO.setPublicationTime(row.getCell(6).getStringCellValue());
                }
                list.add(bookVO);
            }
            return list;
        } catch (IOException e) {
            log.info("解析错误--------");
            throw new ExcelErrorException();
        }
    }
}
