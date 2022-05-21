package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Book;
import ru.otus.springdata.domain.Comment;
import ru.otus.springdata.repository.BookRepository;
import ru.otus.springdata.repository.CommentRepository;

import javax.persistence.EntityNotFoundException;
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

        if (wantedBook.isEmpty()) throw new EntityNotFoundException();

        wantedBook.get().getComments().add(newComment);
        newComment.setBook(wantedBook.get());

        commentRepository.save(newComment);
    }

    public Optional<Comment> getById(long id) {
        return commentRepository.findById(id);
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
        if (existingComment.isEmpty()) throw new EntityNotFoundException();
        existingComment.get().setText(text);
    }
}
