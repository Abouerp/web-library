package com.abouerp.zsc.library.dto;

import lombok.Data;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
public class ProblemManageDTO {
    private Integer id;
    private String title;
    private Boolean show;
    private String createBy;
    private String updateBy;
    private Instant createTime;
    private Instant updateTime;
    private Integer sort;
    private String icon;
}
