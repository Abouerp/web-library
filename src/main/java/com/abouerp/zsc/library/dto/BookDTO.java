package com.abouerp.zsc.library.dto;

import com.abouerp.zsc.library.domain.BookCategory;
import lombok.Data;


/**
 * @author Abouerp
 */
@Data
public class BookDTO {

    private Integer id;
    private String name;
    private String code;
    private String isbn;
    private String author;
    private String publisher;
    private String description;
    private Double price;
    private String publicationTime;
    private String address;
    private BookCategory bookCategory;
}
