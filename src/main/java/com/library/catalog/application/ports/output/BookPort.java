package com.library.catalog.application.ports.output;

import com.library.catalog.domain.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookPort {

    Book create(Book book);

    List<Book> findByAuthorId(UUID authorId);
}
