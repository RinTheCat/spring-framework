package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orm.otus.domain.Author;
import ru.orm.otus.repository.*;

import java.util.List;

@Service
public class AuthorBusinessService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorBusinessService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public Long count() {
        return authorDao.count();
    }

    @Transactional
    public Author insert(Author author) {
        return authorDao.insert(author);
    }

    @Transactional
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        authorDao.deleteById(id);
    }

    @Transactional
    public List<Author> getByFullName(String fullName) {
        return authorDao.getByFullName(fullName);
    }

    @Transactional
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Transactional
    public void updateNameById(long id, String fullName) {
        Author existingAuthor = authorDao.getById(id);
        if (existingAuthor != null) {
            existingAuthor.setFullName(fullName);
        }
    }
}
