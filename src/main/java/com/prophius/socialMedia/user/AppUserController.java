package com.prophius.socialMedia.user;

import com.prophius.socialMedia.exception.ApiResponse;
import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.mailService.EmailService;
import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import com.prophius.socialMedia.user.dto.LoginRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import static com.prophius.socialMedia.token.TokenType.VERIFICATION;
import static com.prophius.socialMedia.utils.ConstantUtils.SOCIAL_SPACE_EMAIL;

@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class AppUserController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerNewUser(@RequestBody @Valid AppUserRegistrationDTO request) {
        AppUser user = authService.registerNewUser(request);
        generateVerificationToken(user);
        return new ResponseEntity<>(new ApiResponse
                (true, "Registration Successful and Pending verification, " +
                        "Please check your email for activation Link"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyUser(@RequestParam("token") String token) {
        authService.confirmVerificationToken(token);
        return new ResponseEntity<>(new ApiResponse
                (true, "User is successfully verified, Please Login to Continue"), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{followUserId}")
    public ResponseEntity<?> followUser(@PathVariable String userId, @PathVariable String followUserId) {
        try {
            authService.followUser(userId, followUserId);
            return ResponseEntity.ok().build();
        } catch (GenericException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/unfollow/{unfollowUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable String userId, @PathVariable String unfollowUserId) {
        try {
            authService.unfollowUser(userId, unfollowUserId);
            return ResponseEntity.ok().build();
        } catch (GenericException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    private void sendVerificationEmail(String email, String name, String verificationLink) {
        NotificationRequest request = new NotificationRequest();
        Context context = new Context();
        context.setVariable("userName", name);
        context.setVariable("verificationLink", verificationLink);
        request.setTo(email);
        request.setSubject("Welcome to Social space");
        request.setContent("welcome");
        request.setContext(context);
        request.setSender(SOCIAL_SPACE_EMAIL);
        emailService.sendEmail(request);
    }

    private void generateVerificationToken(AppUser user) {
        Token token = authService.createVerificationToken(user, VERIFICATION.toString());
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String verificationUrl = baseUrl + "/verify?token=" + token.getToken();
        sendVerificationEmail(user.getEmail(), user.getFirstName(), verificationUrl);
    }
}
