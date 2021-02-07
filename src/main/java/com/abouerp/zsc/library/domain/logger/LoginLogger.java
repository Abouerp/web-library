package com.abouerp.zsc.library.domain.logger;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Table
@Data
@Entity
@Accessors(chain = true)
public class LoginLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String ip;
    //国家
    private String country;
    //省
    private String region;
    //运行商
    private String isp;
    //市
    private String city;
    //客户端
    private String client;
    private String operatingSystem;
    private String description;
    @Enumerated(EnumType.STRING)
    private LoginStatusEnum status;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
