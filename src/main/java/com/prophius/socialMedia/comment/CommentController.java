package com.prophius.socialMedia.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CreateCommentRequest request) {
        CommentDTO comment = commentService.addCommentToPost(request.getUserId(), request.getPostId(), request.getContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}

