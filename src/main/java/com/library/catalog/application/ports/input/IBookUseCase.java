package com.library.catalog.application.ports.input;

import com.library.catalog.domain.model.Book;

import java.util.List;
import java.util.UUID;

public interface IBookUseCase {

    Book create(Book book);

    List<Book> findByAuthorAndYear(UUID authorId, Integer year);
}
