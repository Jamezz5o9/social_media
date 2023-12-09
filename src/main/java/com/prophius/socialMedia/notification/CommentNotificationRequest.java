package com.prophius.socialMedia.notification;

import lombok.Data;

@Data
public class CommentNotificationRequest extends NotificationDTO{
    private String commentContent;
}
