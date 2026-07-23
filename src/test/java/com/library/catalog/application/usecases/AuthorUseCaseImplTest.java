package com.library.catalog.application.usecases;

import com.library.catalog.application.ports.output.AuthorPort;
import com.library.catalog.domain.model.Author;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorUseCaseImplTest {

    private static class FakeAuthorPort implements AuthorPort {
        private final List<Author> authors;

        FakeAuthorPort(List<Author> authors) {
            this.authors = authors;
        }

        @Override
        public Author create(Author author) {
            author.setId(UUID.randomUUID());
            return author;
        }

        @Override
        public Optional<Author> findById(UUID id) {
            return authors.stream().filter(a -> a.getId().equals(id)).findFirst();
        }

        @Override
        public List<Author> findAll() {
            return authors;
        }
    }

    @Test
    void createsAuthorWithValidData() {
        AuthorUseCaseImpl useCase = new AuthorUseCaseImpl(new FakeAuthorPort(List.of()));

        Author result = useCase.create(new Author(null, "Gabriel", "García Márquez", "Colombiana"));

        assertEquals("Gabriel", result.getFirstName());
        assertEquals("García Márquez", result.getLastName());
    }

    @Test
    void throwsWhenFirstNameIsBlank() {
        AuthorUseCaseImpl useCase = new AuthorUseCaseImpl(new FakeAuthorPort(List.of()));

        assertThrows(IllegalArgumentException.class,
                () -> useCase.create(new Author(null, "  ", "García Márquez", "Colombiana")));
    }

    @Test
    void throwsWhenLastNameIsBlank() {
        AuthorUseCaseImpl useCase = new AuthorUseCaseImpl(new FakeAuthorPort(List.of()));

        assertThrows(IllegalArgumentException.class,
                () -> useCase.create(new Author(null, "Gabriel", null, "Colombiana")));
    }
}
