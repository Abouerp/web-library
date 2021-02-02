package com.abouerp.zsc.library.mapper;

import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.dto.BookDTO;
import com.abouerp.zsc.library.vo.BookVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toBook(BookVO bookVO);

    BookDTO toDTO(Book book);
}
