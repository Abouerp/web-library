package com.abouerp.zsc.library.vo;

import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class BookVO {
    private String name;
    private String code;
    private String isbn;
    private String author;
    private String publisher;
    private String description;
    private Double price;
    private String publicationTime;
    private String address;
    private Integer bookCategoryId;
}
