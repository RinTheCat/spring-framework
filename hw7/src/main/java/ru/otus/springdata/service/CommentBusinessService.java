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
    public Comment insert(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public void createNewComment(Comment newComment, long bookId) {
        Book wantedBook = bookRepository.getById(bookId);

        if (wantedBook == null) throw new EntityNotFoundException();

        wantedBook.getComments().add(newComment);
        newComment.setBook(wantedBook);
    }

    public Comment getById(long id) {
        return commentRepository.getById(id);
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
        Comment existingComment = commentRepository.getById(id);
        existingComment.setText(text);
    }
}
