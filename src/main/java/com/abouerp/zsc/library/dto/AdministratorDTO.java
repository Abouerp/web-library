package com.abouerp.zsc.library.dto;


import com.abouerp.zsc.library.domain.user.Role;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class AdministratorDTO {

    private Integer id;
    private String username;
    private String mobile;
    private String email;
    private String sex;
    private String md5;
    private Set<Role> roles;
    private String description;
    private String nickName;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String createBy;
    private String  updateBy;
    private Instant createTime;
    private Instant updateTime;
}
