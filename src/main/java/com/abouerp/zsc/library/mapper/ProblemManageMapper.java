package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.ProblemManage;
import com.abouerp.zsc.library.dto.ProblemManageDTO;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProblemManageMapper {
    ProblemManageMapper INSTANCE = Mappers.getMapper(ProblemManageMapper.class);

    ProblemManage toModle(ProblemManageVO problemManageVO);

    ProblemManageDTO toDTO(ProblemManage problemManage);
}
