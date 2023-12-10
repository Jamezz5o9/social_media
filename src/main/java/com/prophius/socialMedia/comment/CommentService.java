package com.prophius.socialMedia.comment;

public interface CommentService {
    Comment addCommentToPost(Long userId, Long postId, String content);
}
