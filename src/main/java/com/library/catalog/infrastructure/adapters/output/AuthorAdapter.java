package com.library.catalog.infrastructure.adapters.output;

import com.library.catalog.application.ports.output.AuthorPort;
import com.library.catalog.domain.model.Author;
import com.library.catalog.infrastructure.adapters.output.mapper.AuthorEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthorAdapter implements AuthorPort {

    private final AuthorEntityRepository repository;

    public AuthorAdapter(AuthorEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author create(Author author) {
        AuthorEntity authorEntity = repository.save(AuthorEntityMapper.INSTANCE.toAuthorEntity(author));
        return AuthorEntityMapper.INSTANCE.toAuthor(authorEntity);
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return repository.findById(id).map(AuthorEntityMapper.INSTANCE::toAuthor);
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll().stream()
                .map(AuthorEntityMapper.INSTANCE::toAuthor)
                .toList();
    }
}
