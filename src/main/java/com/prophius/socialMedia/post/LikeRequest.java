package com.prophius.socialMedia.post;

import lombok.Data;

@Data
public class LikeRequest {
    private Long userId;
    private Long postId;

}
