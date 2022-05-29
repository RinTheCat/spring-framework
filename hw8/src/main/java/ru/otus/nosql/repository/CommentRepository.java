package ru.otus.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nosql.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findCommentsByBook_Id(String id);
}
