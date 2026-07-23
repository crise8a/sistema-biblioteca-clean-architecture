package com.library.catalog.domain.service;

import com.library.catalog.domain.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookServiceTest {

    private Book book(String title, Integer year) {
        return new Book(UUID.randomUUID(), title, year, UUID.randomUUID());
    }

    @Test
    void filtersBooksByExactYear() {
        List<Book> books = List.of(
                book("Cien años de soledad", 1967),
                book("El otoño del patriarca", 1975),
                book("Crónica de una muerte anunciada", 1981)
        );

        List<Book> result = BookService.filterByYear(books, 1975);

        assertEquals(1, result.size());
        assertEquals("El otoño del patriarca", result.get(0).getTitle());
    }

    @Test
    void returnsEmptyListWhenNoBookMatchesYear() {
        List<Book> books = List.of(book("Cien años de soledad", 1967));

        List<Book> result = BookService.filterByYear(books, 2000);

        assertTrue(result.isEmpty());
    }

    @Test
    void returnsEmptyListWhenBooksListIsNull() {
        List<Book> result = BookService.filterByYear(null, 1967);

        assertTrue(result.isEmpty());
    }

    @Test
    void returnsEmptyListWhenYearIsNull() {
        List<Book> books = List.of(book("Cien años de soledad", 1967));

        List<Book> result = BookService.filterByYear(books, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void excludesBooksWithNullPublicationYear() {
        List<Book> books = List.of(book("Sin año", null));

        List<Book> result = BookService.filterByYear(books, 1967);

        assertTrue(result.isEmpty());
    }
}
