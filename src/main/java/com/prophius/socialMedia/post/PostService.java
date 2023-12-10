package com.prophius.socialMedia.post;

public interface PostService {
    PostDTO createPost(Long userId, String content);
    void likePost(Long userId, Long postId);
    public void unlikePost(Long userId, Long postId);
}
