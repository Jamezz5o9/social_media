package com.prophius.socialMedia.post;

import lombok.Data;

@Data
public class LikeRequest {
    private String userId;
    private String postId;

}
