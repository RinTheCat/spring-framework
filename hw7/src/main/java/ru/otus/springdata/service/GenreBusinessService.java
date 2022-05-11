package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Genre;
import ru.otus.springdata.repository.GenreRepository;

import java.util.List;

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

    @Transactional
    public Genre insert(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getById(long id) {
        return genreRepository.getById(id);
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
        Genre existingGenre = genreRepository.getById(id);
        existingGenre.setName(name);
    }
}