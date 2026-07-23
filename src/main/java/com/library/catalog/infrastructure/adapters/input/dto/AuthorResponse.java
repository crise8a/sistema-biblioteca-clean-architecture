package com.library.catalog.infrastructure.adapters.input.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String nationality;
}
