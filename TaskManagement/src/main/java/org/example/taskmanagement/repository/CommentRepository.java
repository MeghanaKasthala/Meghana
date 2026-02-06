package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
