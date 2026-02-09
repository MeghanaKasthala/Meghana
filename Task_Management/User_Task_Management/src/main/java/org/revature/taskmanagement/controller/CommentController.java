package org.revature.taskmanagement.controller;

import java.util.List;

import org.revature.taskmanagement.dto.CommentDTO;
import org.revature.taskmanagement.model.Comment;
import org.revature.taskmanagement.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO dto) {
        Comment comment = commentService.addComment(dto);
        if (comment == null) {
            return ResponseEntity.badRequest().body("Task or User not found");
        }
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Comment>> getByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.badRequest().body("Comment not found");
        }
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        if (!deleted) {
            return ResponseEntity.badRequest().body("Comment not found");
        }
        return ResponseEntity.ok("Comment deleted");
    }
}