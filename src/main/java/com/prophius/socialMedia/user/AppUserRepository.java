package com.prophius.socialMedia.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

    @Query("SELECT u FROM app_user u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<AppUser> findByEmailIgnoreCase(String email);

}
