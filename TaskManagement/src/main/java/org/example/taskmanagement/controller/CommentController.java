package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Comment;
import org.example.taskmanagement.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{taskId}/comments")
    public Comment addComment(@PathVariable Long taskId,
                              @RequestBody Map<String, String> body) {

        return commentService.addComment(taskId, body.get("text"));
    }
}
