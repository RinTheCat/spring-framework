package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.repository.GenreDao;

import java.util.List;

@Service
public class GenreBusinessService {

    private final GenreDao genreDao;

    @Autowired
    public GenreBusinessService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Long count() {
        return genreDao.count();
    }

    @Transactional
    public Genre insert(Genre genre) {
        return genreDao.insert(genre);
    }

    @Transactional
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        genreDao.deleteById(id);
    }

    @Transactional
    public List<Genre> getByName(String name) {
        return genreDao.getByName(name);
    }

    @Transactional
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Transactional
    public void updateNameById(long id, String name) {
        Genre existingGenre = genreDao.getById(id);
        if (existingGenre != null) {
            existingGenre.setName(name);
        }
    }
}