package ru.otus.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.springdata.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.book where c.id = :id")
    Comment getById(Long id);

    @Query("select c from Comment c join fetch c.book")
    List<Comment> findAll();
}
