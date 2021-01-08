package com.abouerp.zsc.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Abouerp
 */
@Setter
@Getter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table
@Entity
public class BookDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //索书号(由对应书籍的code后4位的数字+1)
    private String searchCode;
    private String address;
    private String backTime;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    //借阅次数
    private int borrowingTimes;
    //续借次数
    private int renewalTimes;
    @ManyToOne
    private Book book;

}
