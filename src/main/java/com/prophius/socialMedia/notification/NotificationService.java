package com.prophius.socialMedia.notification;

import com.prophius.socialMedia.post.Post;
import com.prophius.socialMedia.user.AppUser;

public interface NotificationService {
    void createLikeNotification(AppUser user, Post post);

    void createCommentNotification(AppUser user, Post post, String commentContent);
}
