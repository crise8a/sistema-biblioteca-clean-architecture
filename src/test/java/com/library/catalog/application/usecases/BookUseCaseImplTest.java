package com.library.catalog.application.usecases;

import com.library.catalog.application.ports.output.AuthorPort;
import com.library.catalog.application.ports.output.BookPort;
import com.library.catalog.domain.model.Author;
import com.library.catalog.domain.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookUseCaseImplTest {

    private static class FakeBookPort implements BookPort {
        private final List<Book> books;

        FakeBookPort(List<Book> books) {
            this.books = books;
        }

        @Override
        public Book create(Book book) {
            book.setId(UUID.randomUUID());
            return book;
        }

        @Override
        public List<Book> findByAuthorId(UUID authorId) {
            return books.stream().filter(b -> b.getAuthorId().equals(authorId)).toList();
        }
    }

    private static class FakeAuthorPort implements AuthorPort {
        private final UUID knownAuthorId;

        FakeAuthorPort(UUID knownAuthorId) {
            this.knownAuthorId = knownAuthorId;
        }

        @Override
        public Author create(Author author) {
            throw new UnsupportedOperationException("not needed for this test");
        }

        @Override
        public Optional<Author> findById(UUID id) {
            if (id.equals(knownAuthorId)) {
                return Optional.of(new Author(knownAuthorId, "Gabriel", "García Márquez", "Colombiana"));
            }
            return Optional.empty();
        }

        @Override
        public List<Author> findAll() {
            return List.of();
        }
    }

    @Test
    void createsBookWhenAuthorExists() {
        UUID authorId = UUID.randomUUID();
        BookUseCaseImpl useCase = new BookUseCaseImpl(new FakeBookPort(List.of()), new FakeAuthorPort(authorId));

        Book result = useCase.create(new Book(null, "Cien años de soledad", 1967, authorId));

        assertEquals("Cien años de soledad", result.getTitle());
    }

    @Test
    void throwsWhenAuthorDoesNotExist() {
        UUID authorId = UUID.randomUUID();
        BookUseCaseImpl useCase = new BookUseCaseImpl(new FakeBookPort(List.of()), new FakeAuthorPort(authorId));

        assertThrows(IllegalArgumentException.class,
                () -> useCase.create(new Book(null, "Cien años de soledad", 1967, UUID.randomUUID())));
    }

    @Test
    void filtersBooksByAuthorAndYear() {
        UUID authorId = UUID.randomUUID();
        UUID otherAuthorId = UUID.randomUUID();
        List<Book> books = List.of(
                new Book(UUID.randomUUID(), "Cien años de soledad", 1967, authorId),
                new Book(UUID.randomUUID(), "El otoño del patriarca", 1975, authorId),
                new Book(UUID.randomUUID(), "Otro libro", 1967, otherAuthorId)
        );
        BookUseCaseImpl useCase = new BookUseCaseImpl(new FakeBookPort(books), new FakeAuthorPort(authorId));

        List<Book> result = useCase.findByAuthorAndYear(authorId, 1967);

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).getTitle());
    }

    @Test
    void throwsWhenAuthorIdIsNullOnFilter() {
        BookUseCaseImpl useCase = new BookUseCaseImpl(new FakeBookPort(List.of()), new FakeAuthorPort(UUID.randomUUID()));

        assertThrows(IllegalArgumentException.class, () -> useCase.findByAuthorAndYear(null, 1967));
    }

    @Test
    void throwsWhenYearIsNullOnFilter() {
        UUID authorId = UUID.randomUUID();
        BookUseCaseImpl useCase = new BookUseCaseImpl(new FakeBookPort(List.of()), new FakeAuthorPort(authorId));

        assertThrows(IllegalArgumentException.class, () -> useCase.findByAuthorAndYear(authorId, null));
    }
}
