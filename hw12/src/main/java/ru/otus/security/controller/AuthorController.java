package ru.otus.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.security.domain.Author;
import ru.otus.security.dto.AuthorDto;
import ru.otus.security.dto.BookDto;
import ru.otus.security.service.AuthorBusinessService;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorBusinessService authorBusinessService;

    public AuthorController(AuthorBusinessService authorBusinessService) {
        this.authorBusinessService = authorBusinessService;
    }

    @GetMapping("/authors")
    public String listAuthors(Model model) {
        List<Author> authors = authorBusinessService.getAll();
        Long number = authorBusinessService.count();
        model.addAttribute("authors", authors);
        model.addAttribute("number", number);
        return "allAuthors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable long id) {
        authorBusinessService.deleteById(id);
        return "redirect:/authors";
    }

    @PostMapping("/authors/create")
    public String createAuthor(@RequestParam(value = "fullName") String newFullName) {
        AuthorDto newAuthor = new AuthorDto(newFullName);
        authorBusinessService.insert(newAuthor.toDomainObject());
        return "redirect:/authors";
    }
}
