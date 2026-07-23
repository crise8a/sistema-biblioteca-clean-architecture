package com.library.catalog.infrastructure.adapters.input;

import com.library.catalog.application.ports.input.IBookUseCase;
import com.library.catalog.domain.model.Book;
import com.library.catalog.infrastructure.adapters.input.dto.BookRequest;
import com.library.catalog.infrastructure.adapters.input.dto.BookResponse;
import com.library.catalog.infrastructure.adapters.input.mapper.BookRequestMapper;
import com.library.catalog.infrastructure.adapters.input.mapper.BookResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final IBookUseCase bookUseCase;

    public BookController(IBookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookRequest request) {
        try {
            Book book = bookUseCase.create(BookRequestMapper.INSTANCE.toBook(request));
            return new ResponseEntity<>(
                    BookResponseMapper.INSTANCE.toBookResponse(book),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findByAuthorAndYear(
            @RequestParam UUID authorId,
            @RequestParam Integer year) {
        try {
            List<BookResponse> books = bookUseCase.findByAuthorAndYear(authorId, year).stream()
                    .map(BookResponseMapper.INSTANCE::toBookResponse)
                    .toList();
            return ResponseEntity.ok(books);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
