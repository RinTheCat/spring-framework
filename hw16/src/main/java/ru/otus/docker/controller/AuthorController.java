package ru.otus.docker.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.docker.dto.AuthorDto;
import ru.otus.docker.service.AuthorBusinessService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorBusinessService authorBusinessService;

    public AuthorController(AuthorBusinessService authorBusinessService) {
        this.authorBusinessService = authorBusinessService;
    }

    @GetMapping("/api/author")
    public List<AuthorDto> listAuthors() {
        return authorBusinessService.getAll().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/author/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorBusinessService.deleteById(id);
    }

    @PostMapping("/api/author")
    public void createAuthor(@RequestBody AuthorDto newAuthor) {
        authorBusinessService.insert(newAuthor.toDomainObject());
    }
}
