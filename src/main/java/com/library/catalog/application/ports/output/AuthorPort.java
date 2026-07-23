package com.library.catalog.application.ports.output;

import com.library.catalog.domain.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorPort {

    Author create(Author author);

    Optional<Author> findById(UUID id);

    List<Author> findAll();
}
