package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;
import ru.orm.otus.repository.BookDao;
import ru.orm.otus.repository.CommentDao;

import java.util.List;

@Service
public class CommentBusinessService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Autowired
    public CommentBusinessService(CommentDao commentDao, BookDao bookDao) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    public Long count() {
        return commentDao.count();
    }

    @Transactional
    public Comment insert(Comment comment) {
        return commentDao.insert(comment);
    }

    @Transactional
    public Comment createNewComment(Comment newComment, long bookId) {
        Book wantedBook = bookDao.getById(bookId);
        if (wantedBook == null) return new Comment(0, null, null);

        wantedBook.getComments().add(newComment);

        newComment.setBook(wantedBook);

        return commentDao.insert(newComment);
    }

    @Transactional
    public Comment getById(long id) {
        return commentDao.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

    @Transactional
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Transactional
    public void updateTextById(long id, String text) {
        Comment existingComment = commentDao.getById(id);
        if (existingComment != null) {
            existingComment.setText(text);
        }
    }
}
