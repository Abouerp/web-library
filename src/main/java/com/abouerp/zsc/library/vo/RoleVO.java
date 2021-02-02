package com.abouerp.zsc.library.vo;

import com.abouerp.zsc.library.domain.user.Authority;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class RoleVO {

    private String name;

    private String description;

    private Boolean isDefault;

    private Set<Authority> authorities = new HashSet<>();

}
