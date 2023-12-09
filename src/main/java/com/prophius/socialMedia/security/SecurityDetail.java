package com.prophius.socialMedia.security;

import com.prophius.socialMedia.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SecurityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String token;

    @OneToOne
    private AppUser user;

    private boolean isRevoked;

    private boolean isExpired;
}

