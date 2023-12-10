package com.prophius.socialMedia.post;

import com.prophius.socialMedia.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody LikeRequest request) {
        try {
            postService.likePost(request.getUserId(), request.getPostId());
            return ResponseEntity.ok().build();
        } catch (GenericException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlikePost(@RequestBody LikeRequest request) {
        try {
            postService.unlikePost(request.getUserId(), request.getPostId());
            return ResponseEntity.ok().build();
        } catch (GenericException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest) {
        PostDTO post = postService.createPost(createPostRequest.getUserId(), createPostRequest.getContent());
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}

