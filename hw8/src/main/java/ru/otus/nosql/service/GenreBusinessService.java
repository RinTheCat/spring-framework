package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.exception.NotFoundException;
import ru.otus.nosql.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreBusinessService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreBusinessService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Long count() {
        return genreRepository.count();
    }

    public Genre insert(Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> getById(String id) {
        return genreRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }

    public List<Genre> getByName(String name) {
        return genreRepository.findGenresByName(name);
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    public void updateNameById(String id, String name) {
        Optional<Genre> existingGenre = genreRepository.findById(id);
        if (existingGenre.isEmpty()) throw new NotFoundException(String.format("The book with id=%s was not found", id));
        existingGenre.get().setName(name);
        genreRepository.save(existingGenre.get());
    }
}
