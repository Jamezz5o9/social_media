package com.prophius.socialMedia.post;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String content;
    private int likesCount;
}
