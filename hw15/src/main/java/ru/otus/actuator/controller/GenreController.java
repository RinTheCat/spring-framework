package ru.otus.actuator.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.actuator.dto.GenreDto;
import ru.otus.actuator.service.GenreBusinessService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {

    private final GenreBusinessService genreBusinessService;

    public GenreController(GenreBusinessService genreBusinessService) {
        this.genreBusinessService = genreBusinessService;
    }

    @GetMapping("/api/genre")
    public List<GenreDto> listGenres() {
        return genreBusinessService.getAll().stream().map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/genre/{id}")
    public void deleteGenre(@PathVariable long id) {
        genreBusinessService.deleteById(id);
    }

    @PostMapping("/api/genre")
    public void createGenre(@RequestBody GenreDto newGenre) {
        genreBusinessService.insert(newGenre.toDomainObject());
    }
}
