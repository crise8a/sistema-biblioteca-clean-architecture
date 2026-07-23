package com.library.catalog.infrastructure.adapters.input.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private UUID id;
    private String title;
    private Integer publicationYear;
    private UUID authorId;
}
