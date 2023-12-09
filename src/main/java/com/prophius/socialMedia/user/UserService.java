package com.prophius.socialMedia.user;

import java.util.Optional;

public interface UserService {
    Optional<AppUser> findByEmailIgnoreCase(String email);
}
