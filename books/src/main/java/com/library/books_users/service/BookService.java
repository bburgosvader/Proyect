package com.library.books_users.service;

import java.util.List;
import java.util.UUID;

import com.library.books_users.dto.ApiResponse;
import com.library.books_users.dto.BookDto;
import com.library.books_users.dto.BookSearchDto;


public interface BookService {
    
    ApiResponse<List<BookDto>> searchBooks(BookSearchDto searchDto);
    ApiResponse<BookDto> createBook(BookDto bookDto);
    ApiResponse<BookDto> getBookById(UUID id);
    ApiResponse<BookDto> updateBook(UUID id, BookDto bookDto);
    ApiResponse<Void> deleteBook(UUID id);
    ApiResponse<List<BookDto>> getAllBooks();
} 
