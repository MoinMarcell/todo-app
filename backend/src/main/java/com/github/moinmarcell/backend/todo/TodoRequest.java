package com.github.moinmarcell.backend.todo;

public record TodoRequest(
        String title,
        String description,
        String author
) {
}
