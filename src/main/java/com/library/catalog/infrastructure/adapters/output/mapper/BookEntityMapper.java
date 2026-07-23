package com.library.catalog.infrastructure.adapters.output.mapper;

import com.library.catalog.domain.model.Book;
import com.library.catalog.infrastructure.adapters.output.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookEntityMapper {

    BookEntityMapper INSTANCE = Mappers.getMapper(BookEntityMapper.class);

    BookEntity toBookEntity(Book book);
    Book toBook(BookEntity bookEntity);
}
