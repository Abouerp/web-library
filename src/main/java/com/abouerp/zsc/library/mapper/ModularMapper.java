package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.Modular;
import com.abouerp.zsc.library.vo.ModularVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModularMapper {

    ModularMapper INSTANCE = Mappers.getMapper(ModularMapper.class);

    Modular toAdmin(ModularVO modularVO);

}
