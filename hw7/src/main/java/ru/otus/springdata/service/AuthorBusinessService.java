package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Author;
import ru.otus.springdata.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorBusinessService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorBusinessService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Long count() {
        return authorRepository.count();
    }

    @Transactional
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    public Author getById(long id) {
        return authorRepository.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> getByFullName(String fullName) {
        return authorRepository.findAuthorsByFullName(fullName);
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public void updateNameById(long id, String fullName) {
        Author existingAuthor = authorRepository.getById(id);
        existingAuthor.setFullName(fullName);
    }
}
