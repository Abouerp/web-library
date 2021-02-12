package com.abouerp.zsc.library.vo;

import lombok.Data;


/**
 * @author Abouerp
 */
@Data
public class ProblemManageVO {
    private String title;
    private String text;
    private Boolean show;
    private Integer sortValue;
    private String icon;
}
