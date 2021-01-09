package com.abouerp.zsc.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Setter
@Getter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table
@Entity
@ToString
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    //根据类别（4位code） + 随机4位生成
    private String code;
    private String isbn;
    //作者
    private String author;
    //出版社
    private String publisher;
    //描述
    private String description;
    //价格
    private Double price;
    //出版时间
    private String publicationTime;
    @ManyToOne
    private BookCategory bookCategory;
    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
