package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.exception.NotFoundException;
import ru.otus.nosql.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

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

    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    public List<Author> getByFullName(String fullName) {
        return authorRepository.findAuthorsByFullName(fullName);
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public void updateNameById(String id, String fullName) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isEmpty()) throw new NotFoundException(String.format("The author with id=%s was not found", id));
        existingAuthor.get().setFullName(fullName);
        authorRepository.save(existingAuthor.get());
    }
}
