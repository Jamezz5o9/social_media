package com.prophius.socialMedia.user;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.mailService.EmailService;
import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.token.TokenRepository;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import com.prophius.socialMedia.user.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.prophius.socialMedia.token.TokenType.VERIFICATION;
import static com.prophius.socialMedia.utils.ConstantUtils.EXPIRATION;
import static com.prophius.socialMedia.utils.ConstantUtils.SOCIAL_SPACE_EMAIL;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Override
    public AppUser registerNewUser(AppUserRegistrationDTO userRegistrationRequest) {

        Set<AppUser> followers = new HashSet<>();
        Set<AppUser> following = new HashSet<>();

        AppUser user = AppUser.builder()
                .username(userRegistrationRequest.getUsername())
                .lastName(userRegistrationRequest.getLastName())
                .firstName(userRegistrationRequest.getFirstName())
                .email(userRegistrationRequest.getEmail())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .followers(followers)
                .following(following)
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    public void followUser(String userId, String followUserId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<AppUser> followUserOpt = userRepository.findById(followUserId);

        if (userOpt.isPresent() && followUserOpt.isPresent()) {
            AppUser user = userOpt.get();
            AppUser followUser = followUserOpt.get();

            user.getFollowing().add(followUser);
            followUser.getFollowers().add(user);

            userRepository.save(user);
            userRepository.save(followUser);
        } else {
            // Handle the case where one or both users are not found
            throw new GenericException("User not found");
        }
    }

    public void unfollowUser(String userId, String unfollowUserId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<AppUser> unfollowUserOpt = userRepository.findById(unfollowUserId);

        if (userOpt.isPresent() && unfollowUserOpt.isPresent()) {
            AppUser user = userOpt.get();
            AppUser unfollowUser = unfollowUserOpt.get();

            user.getFollowing().remove(unfollowUser);
            unfollowUser.getFollowers().remove(user);

            userRepository.save(user);
            userRepository.save(unfollowUser);
        } else {
            // Handle the case where one or both users are not found
            throw new GenericException("User not found");
        }

}


    @Override
    public void confirmVerificationToken(String verificationToken) {
        Token token = getAToken(verificationToken, VERIFICATION.toString());

        if (!isValidToken(token.getExpiryDate()))
            throw new GenericException("Token has expired");

        AppUser user = token.getUser();
        user.setVerified(true);
        saveAUser(user);
        sendWelcomeEmail(user.getEmail(), user.getFirstName());
        tokenRepository.delete(token);
    }

    private Token getAToken(String verificationToken, String tokenType) {
        return tokenRepository.findByTokenAndTokenType(verificationToken, tokenType)
                .orElseThrow(() -> new GenericException("Invalid token"));
    }

    private boolean isValidToken(LocalDateTime expiryDate) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), expiryDate);
        return minutes >= 0;
    }

    private void sendWelcomeEmail(String email, String firstName) {
        NotificationRequest request = new NotificationRequest();
        Context context = new Context();
        context.setVariable("userName", firstName);
        request.setTo(email);
        request.setSubject("Welcome to TaskSpace");
        request.setContent("ThankYou");
        request.setContext(context);
        request.setSender(SOCIAL_SPACE_EMAIL);
        emailService.sendEmail(request);
    }

    private AppUser saveAUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public Token createVerificationToken(AppUser user, String tokenType) {
        String token = UUID.randomUUID().toString();
        Optional<Token> optionalToken = tokenRepository.findByUser(user);
        Token verificationToken;
        if (optionalToken.isPresent()) {
            verificationToken = optionalToken.get();
            verificationToken.setToken(token);
            verificationToken.setTokenType(tokenType);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(EXPIRATION));
        } else verificationToken = new Token(token, user, tokenType);
        return tokenRepository.save(verificationToken);
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        return null;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new GenericException(format("user with email %s not found", email)));
    }


}
