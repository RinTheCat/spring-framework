package ru.otus.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.web.domain.Genre;
import ru.otus.web.repository.GenreRepository;

import ru.otus.web.exception.NotFoundException;

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

    public Genre getById(long id) {
        return genreRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    public List<Genre> getByName(String name) {
        return genreRepository.getGenresByName(name);
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    public void updateNameById(long id, String name) {
        Optional<Genre> existingGenre = genreRepository.findById(id);
        if (existingGenre.isEmpty()) throw new NotFoundException();
        existingGenre.get().setName(name);
    }
}