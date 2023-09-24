package com.github.moinmarcell.backend.todo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "todos")
public record Todo(
        @MongoId
        String id,
        String title,
        String description,
        String author,
        LocalDateTime createdAt
) {
}
