package com.library.catalog.infrastructure.adapters.input;

import com.library.catalog.application.ports.input.IBookUseCase;
import com.library.catalog.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IBookUseCase bookUseCase;

    @Test
    void createsBookAndReturnsCreated() throws Exception {
        UUID authorId = UUID.randomUUID();
        when(bookUseCase.create(any(Book.class)))
                .thenReturn(new Book(UUID.randomUUID(), "Cien años de soledad", 1967, authorId));

        mockMvc.perform(post("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Cien años de soledad","publicationYear":1967,"authorId":"%s"}
                                """.formatted(authorId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Cien años de soledad"));
    }

    @Test
    void returnsBadRequestWhenAuthorDoesNotExist() throws Exception {
        when(bookUseCase.create(any(Book.class)))
                .thenThrow(new IllegalArgumentException("No existe un autor con id ..."));

        mockMvc.perform(post("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Cien años de soledad","publicationYear":1967,"authorId":"%s"}
                                """.formatted(UUID.randomUUID())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void filtersBooksByAuthorAndYear() throws Exception {
        UUID authorId = UUID.randomUUID();
        when(bookUseCase.findByAuthorAndYear(any(UUID.class), anyInt()))
                .thenReturn(List.of(new Book(UUID.randomUUID(), "Cien años de soledad", 1967, authorId)));

        mockMvc.perform(get("/v1/books")
                        .param("authorId", authorId.toString())
                        .param("year", "1967"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Cien años de soledad"));
    }

    @Test
    void returnsBadRequestWhenRequestParamsAreMissing() throws Exception {
        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isBadRequest());
    }
}
