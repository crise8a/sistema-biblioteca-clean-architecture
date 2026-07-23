package com.library.catalog.infrastructure.adapters.input.mapper;

import com.library.catalog.domain.model.Author;
import com.library.catalog.infrastructure.adapters.input.dto.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorResponseMapper {

    AuthorResponseMapper INSTANCE = Mappers.getMapper(AuthorResponseMapper.class);

    AuthorResponse toAuthorResponse(Author author);
}
