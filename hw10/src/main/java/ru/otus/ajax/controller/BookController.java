package ru.otus.ajax.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ajax.dto.BookDto;
import ru.otus.ajax.service.BookBusinessService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookBusinessService bookBusinessService;

    public BookController(BookBusinessService bookBusinessService) {
        this.bookBusinessService = bookBusinessService;
    }

    @GetMapping("/api/book")
    public List<BookDto> listBooks() {
        return bookBusinessService.getAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/api/book/{id}")
    public void editBook(@PathVariable long id, @RequestParam(value = "title") String newTitle) {
        bookBusinessService.updateTitleById(id, newTitle);
    }

    @PostMapping("/api/book")
    public void createBook(@RequestBody BookDto newBook) {
        bookBusinessService.createNewBook(newBook.toDomainObject(), newBook.getAuthor().getId(), newBook.getGenre().getId());
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookBusinessService.deleteById(id);
    }
}