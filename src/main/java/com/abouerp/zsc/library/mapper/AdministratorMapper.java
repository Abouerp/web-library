package com.abouerp.zsc.library.mapper;


import com.abouerp.zsc.library.domain.Administrator;
import com.abouerp.zsc.library.domain.Authority;
import com.abouerp.zsc.library.domain.Role;
import com.abouerp.zsc.library.dto.AdministratorDTO;
import com.abouerp.zsc.library.security.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministratorMapper {

    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);


    AdministratorDTO toDTO(Administrator administrator);

//    Administrator toAdmin(AdministratorVO adminVO);

    default UserPrincipal toUserPrincipal(Administrator administrator) {
        List<SimpleGrantedAuthority> authorities = administrator.getRoles()
                .parallelStream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(Authority::springAuthority)
                .collect(Collectors.toList());
        return new UserPrincipal(
                administrator.getId(),
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getEnabled(),
                administrator.getAccountNonExpired(),
                administrator.getCredentialsNonExpired(),
                administrator.getAccountNonLocked(),
                authorities
        );
    }
}
