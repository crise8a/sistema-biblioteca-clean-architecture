package com.library.catalog.infrastructure.adapters.output;

import com.library.catalog.application.ports.output.BookPort;
import com.library.catalog.domain.model.Book;
import com.library.catalog.infrastructure.adapters.output.mapper.BookEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BookAdapter implements BookPort {

    private final BookEntityRepository repository;

    public BookAdapter(BookEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book create(Book book) {
        BookEntity bookEntity = repository.save(BookEntityMapper.INSTANCE.toBookEntity(book));
        return BookEntityMapper.INSTANCE.toBook(bookEntity);
    }

    @Override
    public List<Book> findByAuthorId(UUID authorId) {
        return repository.findByAuthorId(authorId).stream()
                .map(BookEntityMapper.INSTANCE::toBook)
                .toList();
    }
}
