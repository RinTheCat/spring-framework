package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Author;
import ru.otus.springdata.repository.AuthorRepository;

import javax.persistence.EntityNotFoundException;
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

    public Optional<Author> getById(long id) {
        return authorRepository.findById(id);
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
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isEmpty()) throw new EntityNotFoundException();
        existingAuthor.get().setFullName(fullName);
    }
}
