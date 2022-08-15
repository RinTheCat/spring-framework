package ru.otus.docker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.docker.domain.Book;
import ru.otus.docker.domain.Comment;
import ru.otus.docker.repository.BookRepository;
import ru.otus.docker.repository.CommentRepository;

import ru.otus.docker.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentBusinessService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentBusinessService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public Long count() {
        return commentRepository.count();
    }

    @Transactional
    public void createNewComment(Comment newComment, long bookId) {
        Optional<Book> wantedBook = bookRepository.findById(bookId);

        if (wantedBook.isEmpty()) throw new NotFoundException();

        wantedBook.get().getComments().add(newComment);
        newComment.setBook(wantedBook.get());

        commentRepository.save(newComment);
    }

    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public void updateTextById(long id, String text) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isEmpty()) throw new NotFoundException();
        existingComment.get().setText(text);
    }
}
