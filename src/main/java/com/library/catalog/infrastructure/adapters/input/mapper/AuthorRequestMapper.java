package com.library.catalog.infrastructure.adapters.input.mapper;

import com.library.catalog.domain.model.Author;
import com.library.catalog.infrastructure.adapters.input.dto.AuthorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorRequestMapper {

    AuthorRequestMapper INSTANCE = Mappers.getMapper(AuthorRequestMapper.class);

    Author toAuthor(AuthorRequest request);
}
