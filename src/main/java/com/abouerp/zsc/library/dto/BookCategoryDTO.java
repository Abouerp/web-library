package com.abouerp.zsc.library.dto;

import lombok.Data;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
public class BookCategoryDTO {
    private Integer id;
    private String name;
    private String code;
    private Integer createBy;
    private Integer updateBy;
    private Instant createTime;
    private Instant updateTime;
}
