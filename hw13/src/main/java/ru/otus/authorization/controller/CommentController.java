package ru.otus.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.authorization.dto.CommentDto;
import ru.otus.authorization.service.CommentBusinessService;

@Controller
public class CommentController {

    private final CommentBusinessService commentBusinessService;

    public CommentController(CommentBusinessService commentBusinessService) {
        this.commentBusinessService = commentBusinessService;
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable long id) {
        commentBusinessService.deleteById(id);
        return "redirect:/books/edit/" + id;
    }

    @PostMapping("/comments/edit/{id}")
    public String saveEditedComment(@PathVariable long id, @RequestParam(value = "text") String newText) {
        commentBusinessService.updateTextById(id, newText);
        return "redirect:/books/edit/" + id;
    }

    @PostMapping("/comments/create")
    public String createComment(@RequestParam(value = "text") String newText,
                             @RequestParam(value = "bookId") long bookId) {
        CommentDto newComment = new CommentDto(newText, bookId);
        commentBusinessService.createNewComment(newComment.toDomainObject(), newComment.getBookId());
        return "redirect:/books/edit/" + bookId;
    }
}
