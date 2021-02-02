package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.book.BookCategory;
import com.abouerp.zsc.library.dto.BookCategoryDTO;
import com.abouerp.zsc.library.vo.BookCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookCategoryMapper {

    BookCategoryMapper INSTANCE = Mappers.getMapper(BookCategoryMapper.class);

    BookCategoryDTO toDTO(BookCategory bookCategory);

    BookCategory toModle(BookCategoryVO bookCategoryVO);

}
