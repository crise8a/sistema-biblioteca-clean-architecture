package com.library.catalog.infrastructure.adapters.input;

import com.library.catalog.application.ports.input.IAuthorUseCase;
import com.library.catalog.domain.model.Author;
import com.library.catalog.infrastructure.adapters.input.dto.AuthorRequest;
import com.library.catalog.infrastructure.adapters.input.dto.AuthorResponse;
import com.library.catalog.infrastructure.adapters.input.mapper.AuthorRequestMapper;
import com.library.catalog.infrastructure.adapters.input.mapper.AuthorResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final IAuthorUseCase authorUseCase;

    public AuthorController(IAuthorUseCase authorUseCase) {
        this.authorUseCase = authorUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AuthorRequest request) {
        try {
            Author author = authorUseCase.create(AuthorRequestMapper.INSTANCE.toAuthor(request));
            return new ResponseEntity<>(
                    AuthorResponseMapper.INSTANCE.toAuthorResponse(author),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> findAll() {
        List<AuthorResponse> authors = authorUseCase.findAll().stream()
                .map(AuthorResponseMapper.INSTANCE::toAuthorResponse)
                .toList();
        return ResponseEntity.ok(authors);
    }
}
