package com.library.catalog.application.usecases;

import com.library.catalog.application.ports.input.IBookUseCase;
import com.library.catalog.application.ports.output.AuthorPort;
import com.library.catalog.application.ports.output.BookPort;
import com.library.catalog.domain.model.Book;
import com.library.catalog.domain.service.BookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BookUseCaseImpl implements IBookUseCase {

    private final BookPort bookPort;
    private final AuthorPort authorPort;

    public BookUseCaseImpl(BookPort bookPort, AuthorPort authorPort) {
        this.bookPort = bookPort;
        this.authorPort = authorPort;
    }

    @Override
    public Book create(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("El campo 'title' es requerido y no puede estar vacío");
        }
        if (book.getPublicationYear() == null) {
            throw new IllegalArgumentException("El campo 'publicationYear' es requerido");
        }
        if (book.getAuthorId() == null) {
            throw new IllegalArgumentException("El campo 'authorId' es requerido");
        }
        authorPort.findById(book.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un autor con id " + book.getAuthorId()));

        return bookPort.create(book);
    }

    @Override
    public List<Book> findByAuthorAndYear(UUID authorId, Integer year) {
        if (authorId == null) {
            throw new IllegalArgumentException("El parámetro 'authorId' es requerido");
        }
        if (year == null) {
            throw new IllegalArgumentException("El parámetro 'year' es requerido");
        }
        List<Book> books = bookPort.findByAuthorId(authorId);
        return BookService.filterByYear(books, year);
    }
}
