package com.library.books_users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

    boolean success,
    T data,
    String message
) {

}
