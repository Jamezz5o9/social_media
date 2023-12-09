package com.prophius.socialMedia.post;

public interface PostService {
    Post createPost(String userId, String content);
    void likePost(String userId, String postId);
    public void unlikePost(String userId, String postId);
}
