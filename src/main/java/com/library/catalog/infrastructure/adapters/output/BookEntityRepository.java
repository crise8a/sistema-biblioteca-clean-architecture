package com.library.catalog.infrastructure.adapters.output;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookEntityRepository extends JpaRepository<BookEntity, UUID> {

    List<BookEntity> findByAuthorId(UUID authorId);
}
