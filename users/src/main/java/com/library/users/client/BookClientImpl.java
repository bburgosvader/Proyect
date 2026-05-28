package com.library.users.client;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.library.users.dto.ApiResponse;
import com.library.users.dto.BookDto;
import com.library.users.dto.BookSearchDto;
import com.library.users.exception.ResourceNotFoundException;
@Component
public class BookClientImpl implements BookClient {

    private final RestClient restClient;

    public BookClientImpl(@Value("${book.service.url}") String baseurl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseurl)
                .build();
    }

    //Muestra todos los libros disponibles
    @Override
    public List<BookDto> getAllBooks() {

        ResponseEntity<ApiResponse<List<BookDto>>> response = restClient.get()
                .uri("/api/books")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<List<BookDto>>>() {
                });

        ApiResponse<List<BookDto>> body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body.data();
        } else {
            throw new ResourceNotFoundException("No se pudieron obtener los libros: " + status);
        }
    }

    //buscar libro por titulo, autor o categoria
    @Override
    public List<BookDto> searchBooks(BookSearchDto searchDto) {
        ResponseEntity<ApiResponse<List<BookDto>>> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/books/search")
                        .queryParamIfPresent("title", java.util.Optional.ofNullable(searchDto.title()))
                        .queryParamIfPresent("author", java.util.Optional.ofNullable(searchDto.author()))
                        .queryParamIfPresent("category", java.util.Optional.ofNullable(searchDto.category()))
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<List<BookDto>>>() {
                });

        ApiResponse<List<BookDto>> body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body.data();
        } else {
            throw new ResourceNotFoundException("Error al recuperar libros: " + status);
        }
    }

    //Obtener detalles de un libro específico por su ID
    @Override
    public BookDto getBookById(UUID id) {

        ResponseEntity<ApiResponse<BookDto>> response = restClient.get()
                .uri("/api/books/{id}", id)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<BookDto>>() {
                });

        ApiResponse<BookDto> body = response.getBody();

        if (response.getStatusCode().is2xxSuccessful() && body != null && body.success()) {
            return body.data();
        }

        throw new ResourceNotFoundException("Error al obtener libro por ID: " + response.getStatusCode());
}

    
}

    
