package com.prophius.socialMedia.security;

import com.prophius.socialMedia.user.AppUser;
import com.prophius.socialMedia.user.AuthService;
import com.prophius.socialMedia.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SecuredUserService implements UserDetailsService {

    private final UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userService.findByEmailIgnoreCase(email).orElseThrow(
                () -> new UsernameNotFoundException(format("User not found with email %s", email)));
        return UserPrincipal.create(user);
    }
}