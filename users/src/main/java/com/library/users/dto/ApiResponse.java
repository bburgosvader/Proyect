package com.library.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
    boolean success,
    T data,
    ErrorDetails error,
    String message


) {
    public ApiResponse(boolean success,String message, T data) {
        this(success, data, null, message);
    }

    public ApiResponse(boolean success, ErrorDetails error, String message) {
        this(success, null, error, message);
    }

    public ApiResponse(boolean success, T data) {
        this(success, data, null, null);
    }

    public ApiResponse(boolean success, ErrorDetails error) {
        this(success, null, error, null);
    }

}
