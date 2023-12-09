package com.prophius.socialMedia.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String userId;
    private String postId;
    private String content;
}

