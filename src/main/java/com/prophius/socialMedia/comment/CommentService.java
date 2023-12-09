package com.prophius.socialMedia.comment;

public interface CommentService {
    Comment addCommentToPost(String userId, String postId, String content);
}
