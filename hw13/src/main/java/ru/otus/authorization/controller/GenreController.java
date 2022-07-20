package ru.otus.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.authorization.domain.Genre;
import ru.otus.authorization.dto.AuthorDto;
import ru.otus.authorization.dto.GenreDto;
import ru.otus.authorization.service.GenreBusinessService;

import java.util.List;

@Controller
public class GenreController {

    private final GenreBusinessService genreBusinessService;

    public GenreController(GenreBusinessService genreBusinessService) {
        this.genreBusinessService = genreBusinessService;
    }

    @GetMapping("/genres")
    public String listGenres(Model model) {
        List<Genre> genres = genreBusinessService.getAll();
        Long number = genreBusinessService.count();
        model.addAttribute("genres", genres);
        model.addAttribute("number", number);
        return "allGenres";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable long id) {
        genreBusinessService.deleteById(id);
        return "redirect:/genres";
    }

    @PostMapping("/genres/create")
    public String createGenre(@RequestParam(value = "name") String newName) {
        GenreDto newGenre = new GenreDto(newName);
        genreBusinessService.insert(newGenre.toDomainObject());
        return "redirect:/genres";
    }
}
