package com.example.ingress.mapper;

import com.example.ingress.domain.BookEntity;
import com.example.ingress.service.dto.book.BookRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface IngressMapper {
//    BookResponseDto convertToBookResponse(Book book);
    BookEntity convertToBook(BookRequestDto bookRequestDto);
//
//    AuthorResponseDto convertToAuthor(Author author);
//    Author convertToAuthor(AuthorRequestDto authorRequestDto);

}
