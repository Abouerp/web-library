package com.abouerp.zsc.library.domain.logger;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class OperatorLogger implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    //get post delete put path
    private String httpMethod;
    private String username;
    private String param;
    //请求路径
    private String path;
    private String operatingSystem;
    private String client;
    @CreationTimestamp
    private Instant createTime;
    private Long executionTime;
    private String signatureName;
}
