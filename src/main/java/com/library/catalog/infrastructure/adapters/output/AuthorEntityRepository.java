package com.library.catalog.infrastructure.adapters.output;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorEntityRepository extends JpaRepository<AuthorEntity, UUID> {
}
