package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Genre;
import ru.otus.springdata.repository.GenreRepository;

import javax.persistence.EntityNotFoundException;
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

    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
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
        if (existingGenre.isEmpty()) throw new EntityNotFoundException();
        existingGenre.get().setName(name);
    }
}