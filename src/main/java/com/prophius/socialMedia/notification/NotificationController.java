package com.prophius.socialMedia.notification;

import com.prophius.socialMedia.post.Post;
import com.prophius.socialMedia.post.PostRepository;
import com.prophius.socialMedia.user.AppUser;
import com.prophius.socialMedia.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/like")
    public ResponseEntity<?> createLikeNotification(@RequestBody NotificationDTO request) {
        Optional<AppUser> userOpt = userRepository.findById(request.getUserId());
        Optional<Post> postOpt = postRepository.findById(request.getPostId());

        if (userOpt.isPresent() && postOpt.isPresent()) {
            notificationService.createLikeNotification(userOpt.get(), postOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Post not found");
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createCommentNotification(@RequestBody CommentNotificationRequest request) {
        Optional<AppUser> userOpt = userRepository.findById(request.getUserId());
        Optional<Post> postOpt = postRepository.findById(request.getPostId());

        if (userOpt.isPresent() && postOpt.isPresent()) {
            notificationService.createCommentNotification(userOpt.get(), postOpt.get(), request.getCommentContent());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Post not found");
        }
    }
}

