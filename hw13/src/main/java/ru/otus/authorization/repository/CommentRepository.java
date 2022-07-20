package ru.otus.authorization.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.authorization.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "comment", attributePaths = {"book"})
    Optional<Comment> findById(Long id);

    @EntityGraph(value = "comment", attributePaths = {"book"})
    List<Comment> findAll();
}
