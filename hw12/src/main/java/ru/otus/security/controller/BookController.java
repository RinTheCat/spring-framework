package ru.otus.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.security.domain.Book;
import ru.otus.security.dto.BookDto;
import ru.otus.security.service.BookBusinessService;

import java.util.List;

@Controller
public class BookController {

    private final BookBusinessService bookBusinessService;

    public BookController(BookBusinessService bookBusinessService) {
        this.bookBusinessService = bookBusinessService;
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> books = bookBusinessService.getAll();
        Long number = bookBusinessService.count();
        model.addAttribute("books", books);
        model.addAttribute("number", number);
        return "allBooks";
    }

    @PostMapping("/books")
    public String listBooksPost(Model model) {
        List<Book> books = bookBusinessService.getAll();
        Long number = bookBusinessService.count();
        model.addAttribute("books", books);
        model.addAttribute("number", number);
        return "allBooks";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(Model model, @PathVariable long id) {
        Book book = bookBusinessService.getById(id);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/books/edit/{id}")
    public String saveEditedBook(@PathVariable long id, @RequestParam(value = "title") String newTitle) {
        bookBusinessService.updateTitleById(id, newTitle);
        return "redirect:/books";
    }

    @GetMapping("/books/create")
    public String saveCreatedBook() {
        return "createBook";
    }

    @PostMapping("/books/create")
    public String createBook(@RequestParam(value = "title") String newTitle,
                             @RequestParam(value = "authorId") long authorId,
                             @RequestParam(value = "genreId") long genreId) {
        BookDto newBook = new BookDto(newTitle, authorId, genreId);
        bookBusinessService.createNewBook(newBook.toDomainObject(), newBook.getAuthorId(), newBook.getGenreId());
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(Model model, @PathVariable long id) {
        bookBusinessService.deleteById(id);
        return "redirect:/books";
    }
}