package org.revature.taskmanagement.dto;

public class CommentDTO {

    private Long taskId;
    private Long userId;
    private String commentText;

    // getters and setters
    public Long getTaskId() {
        return taskId;
    }
    public void setTaskId(Long taskId) {

        this.taskId = taskId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {

        this.userId = userId;
    }
    public String getCommentText() {
        return commentText;
    }
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}