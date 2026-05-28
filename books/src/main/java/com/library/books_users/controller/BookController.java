package com.library.books_users.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.books_users.dto.ApiResponse;
import com.library.books_users.dto.BookDto;
import com.library.books_users.dto.BookSearchDto;
import com.library.books_users.service.BookService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/books")
public class BookController {

    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    

    //Muestra todos los libros disponibles
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    //Permite buscar libros por título, autor o categoría
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(BookSearchDto searchDto) {
        return ResponseEntity.ok(bookService.searchBooks(searchDto));
    }

    //Permite obtener los detalles de un libro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    
    //Permite agregar un nuevo libro a la biblioteca
    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> createBook(@Valid@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.createBook(bookDto));
    }

    //Permite actualizar la información de un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@Valid@PathVariable UUID id, @Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    //Permite eliminar un libro de la biblioteca
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@Valid@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
