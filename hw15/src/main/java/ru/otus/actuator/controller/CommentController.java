package ru.otus.actuator.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.actuator.dto.CommentDto;
import ru.otus.actuator.service.CommentBusinessService;

@RestController
public class CommentController {

    private final CommentBusinessService commentBusinessService;

    public CommentController(CommentBusinessService commentBusinessService) {
        this.commentBusinessService = commentBusinessService;
    }

    @DeleteMapping("/api/comment/{id}")
    public void deleteComment(@PathVariable long id) {
        commentBusinessService.deleteById(id);
    }

    @PatchMapping("/api/comment/{id}")
    public void editComment(@PathVariable long id, @RequestParam(value = "text") String newText) {
        commentBusinessService.updateTextById(id, newText);
    }

    @PostMapping("/api/comment")
    public void createComment(@RequestBody CommentDto newComment) {
        commentBusinessService.createNewComment(newComment.toDomainObject(), newComment.getBookId());
    }
}
