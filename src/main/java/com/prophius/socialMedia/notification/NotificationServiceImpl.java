package com.prophius.socialMedia.notification;

import com.prophius.socialMedia.post.Post;
import com.prophius.socialMedia.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void createLikeNotification(AppUser user, Post post) {
        Notification notification = new Notification();
        notification.setType("LIKE");
        notification.setContent(user.getUsername() + " liked your post");
        notification.setTargetUser(post.getUser());
        notification.setRelatedPost(post);

        notificationRepository.save(notification);
    }
    @Override
    public void createCommentNotification(AppUser user, Post post, String commentContent) {
        Notification notification = new Notification();
        notification.setType("COMMENT");
        notification.setContent(user.getUsername() + " commented on your post: " + commentContent);
        notification.setTargetUser(post.getUser());
        notification.setRelatedPost(post);

        notificationRepository.save(notification);
    }
}
