package com.library.catalog.infrastructure.adapters.input.mapper;

import com.library.catalog.domain.model.Book;
import com.library.catalog.infrastructure.adapters.input.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookResponseMapper {

    BookResponseMapper INSTANCE = Mappers.getMapper(BookResponseMapper.class);

    BookResponse toBookResponse(Book book);
}
