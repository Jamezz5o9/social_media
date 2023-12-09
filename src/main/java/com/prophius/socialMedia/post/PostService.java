package com.prophius.socialMedia.post;

public interface PostService {
    void likePost(String userId, String postId);
    public void unlikePost(String userId, String postId);
}
