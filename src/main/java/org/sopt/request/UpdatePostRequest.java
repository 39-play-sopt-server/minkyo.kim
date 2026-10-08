package org.sopt.request;

import org.sopt.domain.Category;

import java.util.Optional;

public record UpdatePostRequest(
        Optional<String> title,
        Optional<String> content,
        Optional<Category> category
) {
}
