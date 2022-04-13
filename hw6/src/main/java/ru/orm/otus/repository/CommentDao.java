package ru.orm.otus.repository;

import ru.orm.otus.domain.Comment;

import java.util.List;

public interface CommentDao {

    Long count();

    Comment insert(Comment comment);

    Comment getById(long id);

    void deleteById(long id);

    List<Comment> getAll();
}
