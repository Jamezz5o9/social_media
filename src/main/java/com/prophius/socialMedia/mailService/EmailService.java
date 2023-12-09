package com.prophius.socialMedia.mailService;

import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.notification.NotificationResponse;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<NotificationResponse> sendEmail(NotificationRequest request);
}
