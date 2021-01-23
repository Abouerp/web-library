package com.abouerp.zsc.library.vo;

import lombok.Data;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
public class ProblemManageVO {
    private String title;
    private String text;
    private Boolean show;
}
