package com.prophius.socialMedia.user;

import com.prophius.socialMedia.token.Token;
import com.prophius.socialMedia.user.dto.AppUserRegistrationDTO;
import com.prophius.socialMedia.user.dto.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {
    AppUser registerNewUser(AppUserRegistrationDTO userRegistrationRequest);
    void confirmVerificationToken(String verificationToken);
    Token createVerificationToken(AppUser user, String tokenType);
    String login(LoginRequestDTO loginRequestDTO);

    AppUser findUserByEmail(String username);
}
