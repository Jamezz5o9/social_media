package com.prophius.socialMedia.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long userId;
    private Long postId;
    private String content;
}

