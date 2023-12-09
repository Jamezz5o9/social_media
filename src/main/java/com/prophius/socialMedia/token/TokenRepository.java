package com.prophius.socialMedia.token;

import com.prophius.socialMedia.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByUser(AppUser user);
    Optional<Token> findByTokenAndTokenType(String token, String tokenType);
}
