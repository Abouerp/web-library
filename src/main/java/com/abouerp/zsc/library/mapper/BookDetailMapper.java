package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.book.BookDetail;
import com.abouerp.zsc.library.dto.BookDetailDTO;
import com.abouerp.zsc.library.vo.BookDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookDetailMapper {
    BookDetailMapper INSTANCE = Mappers.getMapper(BookDetailMapper.class);

    BookDetail toBookDetale(BookDetailVO bookDetailVO);

    BookDetailDTO toDTO(BookDetail bookDetail);
}
