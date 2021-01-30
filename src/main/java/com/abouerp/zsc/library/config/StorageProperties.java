package com.abouerp.zsc.library.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件路径
 *
 * @author Abouerp
 */
@Component
@ConfigurationProperties(prefix = "storage")
@Data
public class StorageProperties {
    private String location;
}
