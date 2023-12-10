package com.prophius.socialMedia.comment;

public interface CommentService {
    CommentDTO addCommentToPost(Long userId, Long postId, String content);
}
