package com.library.catalog.domain.model;

import java.util.UUID;

public class Book {

    private UUID id;
    private String title;
    private Integer publicationYear;
    private UUID authorId;

    public Book() {
    }

    public Book(UUID id, String title, Integer publicationYear, UUID authorId) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.authorId = authorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", authorId=" + authorId +
                '}';
    }
}
