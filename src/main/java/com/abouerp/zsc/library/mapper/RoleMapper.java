package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.user.Role;
import com.abouerp.zsc.library.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleVO roleVO);
}
