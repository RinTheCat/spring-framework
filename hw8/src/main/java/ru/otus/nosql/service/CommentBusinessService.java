package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.exception.NotFoundException;
import ru.otus.nosql.repository.BookRepository;
import ru.otus.nosql.repository.CommentRepository;

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
    public void createNewComment(Comment newComment, String bookId) {
        Optional<Book> wantedBook = bookRepository.findById(bookId);

        if (wantedBook.isEmpty()) throw new NotFoundException(String.format("The book with id=%s was not found", bookId));

        newComment.setBook(wantedBook.get());

        commentRepository.save(newComment);
    }

    public Optional<Comment> getById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public void updateTextById(String id, String text) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isEmpty()) throw new NotFoundException(String.format("The comment with id=%s was not found", id));
        existingComment.get().setText(text);
        commentRepository.save(existingComment.get());
    }

    public List<Comment> getByBookId(String bookId) {
        return commentRepository.findCommentsByBook_Id(bookId);
    }
}
