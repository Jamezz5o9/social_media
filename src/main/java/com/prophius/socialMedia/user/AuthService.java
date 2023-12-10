package com.prophius.socialMedia.user;

import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import com.prophius.socialMedia.user.dto.LoginRequestDTO;

import java.util.Map;

public interface AuthService {
    AppUser registerNewUser(AppUserRegistrationDTO userRegistrationRequest);

    void followUser(Long userId, Long followUserId);

    void unfollowUser(Long userId, Long unfollowUserId);

    void confirmVerificationToken(String verificationToken);
    Token createVerificationToken(AppUser user, String tokenType);
    Map<String, Object> login(LoginRequestDTO loginRequestDTO);

    AppUser findUserByEmail(String username);
}
