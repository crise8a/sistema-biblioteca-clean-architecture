package com.library.catalog.infrastructure.adapters.input.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {

    private String title;
    private Integer publicationYear;
    private UUID authorId;
}
