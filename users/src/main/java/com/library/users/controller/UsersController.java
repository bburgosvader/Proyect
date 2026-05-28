package com.library.users.controller;

import com.library.users.client.BookClientImpl;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.users.client.BookClient;
import com.library.users.dto.ApiResponse;
import com.library.users.dto.BookDto;
import com.library.users.dto.BookSearchDto;




@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final BookClientImpl bookClientImpl;
    final BookClient bookClient;

    public UsersController(BookClient bookClient, BookClientImpl bookClientImpl) {
        this.bookClient = bookClient;
        this.bookClientImpl = bookClientImpl;
    }

    // Endpoint para obtener todos los libros disponibles en la biblioteca
    @GetMapping("/books")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        return ResponseEntity.ok(new ApiResponse<>(true, bookClientImpl.getAllBooks()));
    }

    //Endpoint para buscar libros por título, autor o categoría
    @GetMapping("/books/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(BookSearchDto searchDto) {

        return ResponseEntity.ok(new ApiResponse<>(true, bookClientImpl.searchBooks(searchDto)));

    }

    //Endpoint para obtener los detalles de un libro específico por su ID
    @GetMapping("/books/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(true, bookClientImpl.getBookById(id)));
    }
  
    

}
