package com.library.catalog.application.usecases;

import com.library.catalog.application.ports.input.IAuthorUseCase;
import com.library.catalog.application.ports.output.AuthorPort;
import com.library.catalog.domain.model.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorUseCaseImpl implements IAuthorUseCase {

    private final AuthorPort authorPort;

    public AuthorUseCaseImpl(AuthorPort authorPort) {
        this.authorPort = authorPort;
    }

    @Override
    public Author create(Author author) {
        if (author.getFirstName() == null || author.getFirstName().isBlank()) {
            throw new IllegalArgumentException("El campo 'firstName' es requerido y no puede estar vacío");
        }
        if (author.getLastName() == null || author.getLastName().isBlank()) {
            throw new IllegalArgumentException("El campo 'lastName' es requerido y no puede estar vacío");
        }
        return authorPort.create(author);
    }

    @Override
    public List<Author> findAll() {
        return authorPort.findAll();
    }
}
