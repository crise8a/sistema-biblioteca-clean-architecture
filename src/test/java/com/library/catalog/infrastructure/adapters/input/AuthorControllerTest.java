package com.library.catalog.infrastructure.adapters.input;

import com.library.catalog.application.ports.input.IAuthorUseCase;
import com.library.catalog.domain.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAuthorUseCase authorUseCase;

    @Test
    void createsAuthorAndReturnsCreated() throws Exception {
        when(authorUseCase.create(any(Author.class)))
                .thenReturn(new Author(UUID.randomUUID(), "Gabriel", "García Márquez", "Colombiana"));

        mockMvc.perform(post("/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Gabriel","lastName":"García Márquez","nationality":"Colombiana"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Gabriel"));
    }

    @Test
    void returnsBadRequestWhenFirstNameIsBlank() throws Exception {
        when(authorUseCase.create(any(Author.class)))
                .thenThrow(new IllegalArgumentException("El campo 'firstName' es requerido y no puede estar vacío"));

        mockMvc.perform(post("/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"","lastName":"García Márquez","nationality":"Colombiana"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listsAllAuthors() throws Exception {
        when(authorUseCase.findAll())
                .thenReturn(List.of(new Author(UUID.randomUUID(), "Gabriel", "García Márquez", "Colombiana")));

        mockMvc.perform(get("/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Gabriel"));
    }
}
