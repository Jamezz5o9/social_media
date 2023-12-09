package com.prophius.socialMedia.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String userId;
    private String content;
}
