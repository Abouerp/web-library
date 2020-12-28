package com.abouerp.zsc.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 文件上传
 *
 * @author Abouerp
 */
@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table
public class Storage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 唯一标识
     */
    private String md5;
    /**
     * 页面上传时的名称
     */
    private String originalFilename;
    /**
     * 文件类型
     */
    private String contentType;

}
