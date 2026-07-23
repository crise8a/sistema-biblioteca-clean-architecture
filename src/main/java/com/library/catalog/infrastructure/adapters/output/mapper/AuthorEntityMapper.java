package com.library.catalog.infrastructure.adapters.output.mapper;

import com.library.catalog.domain.model.Author;
import com.library.catalog.infrastructure.adapters.output.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorEntityMapper {

    AuthorEntityMapper INSTANCE = Mappers.getMapper(AuthorEntityMapper.class);

    AuthorEntity toAuthorEntity(Author author);
    Author toAuthor(AuthorEntity authorEntity);
}
