package com.prophius.socialMedia.user;

import com.prophius.socialMedia.exception.ApiResponse;
import com.prophius.socialMedia.mailService.EmailService;
import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import static com.prophius.socialMedia.token.TokenType.VERIFICATION;
import static com.prophius.socialMedia.utils.ConstantUtils.SOCIAL_SPACE_EMAIL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
