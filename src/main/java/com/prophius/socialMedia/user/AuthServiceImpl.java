package com.prophius.socialMedia.user;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.mailService.EmailService;
import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.security.JwtService;
import com.prophius.socialMedia.security.SecurityDetail;
import com.prophius.socialMedia.security.SecurityDetailService;
import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.token.TokenRepository;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import com.prophius.socialMedia.user.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final AuthenticationManager authenticationManager;
    private final SecurityDetailService securityDetailService;
    private final JwtService jwtService;

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
    @Transactional
    @Override
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
        } else throw new GenericException("User not found");

    }
    @Transactional
    @Override
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
        } else throw new GenericException("User not found");


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

    @Transactional
    @Override
    public String login(LoginRequestDTO loginRequest)  {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser user = findUserByEmail(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user.getEmail());
        revokeAllUserToken(user.getId());
        saveToken(jwtToken, user);

        return "Login successfully";
    }

    private void saveToken(String jwt, AppUser user) {
        SecurityDetail securityDetail = new SecurityDetail();
        securityDetail.setToken(jwt);
        securityDetail.setExpired(false);
        securityDetail.setRevoked(false);
        securityDetail.setUser(user);
        securityDetailService.save(securityDetail);
    }

    private void revokeAllUserToken(String userId) {
        var allUsersToken = securityDetailService.findSecurityDetailByUserId(userId);
        if (allUsersToken.isEmpty()) return;
        allUsersToken
                .forEach(securityDetail -> {
                    securityDetail.setRevoked(true);
                    securityDetail.setExpired(true);
                    securityDetailService.save(securityDetail);
                });
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new GenericException(format("user with email %s not found", email)));
    }


}
