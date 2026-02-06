package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Comment;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.repository.CommentRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public Comment addComment(Long taskId, String text) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (text == null || text.isBlank()) {
            throw new RuntimeException("Comment text cannot be empty");
        }

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setText(text);

        return commentRepository.save(comment);
    }
}
