package com.library.books_users.dto;

public record BookSearchDto(
    String title,
    String author,
    String category,
    String isbn
    
) {

}
