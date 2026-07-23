package com.library.catalog.application.ports.input;

import com.library.catalog.domain.model.Author;

import java.util.List;

public interface IAuthorUseCase {

    Author create(Author author);

    List<Author> findAll();
}
