package com.library.catalog.infrastructure.adapters.input.mapper;

import com.library.catalog.domain.model.Book;
import com.library.catalog.infrastructure.adapters.input.dto.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookRequestMapper {

    BookRequestMapper INSTANCE = Mappers.getMapper(BookRequestMapper.class);

    Book toBook(BookRequest request);
}
