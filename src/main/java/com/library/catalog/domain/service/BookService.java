package com.library.catalog.domain.service;

import com.library.catalog.domain.model.Book;

import java.util.List;

public class BookService {

    public static List<Book> filterByYear(List<Book> books, Integer year) {
        if (books == null || year == null) return List.of();

        return books.stream()
                .filter(book -> book != null && book.getPublicationYear() != null)
                .filter(book -> book.getPublicationYear().equals(year))
                .toList();
    }
}
