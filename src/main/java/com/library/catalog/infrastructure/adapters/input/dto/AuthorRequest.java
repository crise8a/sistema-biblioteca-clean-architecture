package com.library.catalog.infrastructure.adapters.input.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {

    private String firstName;
    private String lastName;
    private String nationality;
}
