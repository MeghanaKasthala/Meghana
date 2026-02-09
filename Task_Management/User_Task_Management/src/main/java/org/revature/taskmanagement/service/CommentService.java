package org.revature.taskmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.revature.taskmanagement.dto.CommentDTO;
import org.revature.taskmanagement.model.Comment;
import org.revature.taskmanagement.model.Task;
import org.revature.taskmanagement.model.User;
import org.revature.taskmanagement.repository.CommentRepository;
import org.revature.taskmanagement.repository.TaskRepository;
import org.revature.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          TaskRepository taskRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(CommentDTO dto) {
        Optional<Task> taskOpt = taskRepository.findById(dto.getTaskId());
        Optional<User> userOpt = userRepository.findById(dto.getUserId());

        if (taskOpt.isEmpty() || userOpt.isEmpty()) {
            return null;
        }

        Comment comment = new Comment();
        comment.setTask(taskOpt.get());
        comment.setUser(userOpt.get());
        comment.setCommentText(dto.getCommentText());
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public boolean deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            return false;
        }
        commentRepository.deleteById(id);
        return true;
    }
}