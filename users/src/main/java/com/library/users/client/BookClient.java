package com.library.users.client;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;


import com.library.users.dto.BookDto;
import com.library.users.dto.BookSearchDto;

@Component
public interface BookClient {


    List<BookDto> getAllBooks();

    List<BookDto> searchBooks (BookSearchDto searchDto);

    BookDto getBookById(UUID id);

    

}
