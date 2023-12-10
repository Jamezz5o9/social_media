package com.prophius.socialMedia.user;

import com.prophius.socialMedia.audit.DateAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AppUser extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profilePictureUrl;

    @ManyToMany
    @JoinTable(
            name = "app_user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id")
    )
    private Set<AppUser> followers;

    @ManyToMany
    @JoinTable(
            name = "app_user_following",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<AppUser> following;

    private boolean isVerified;

    private String role;
}
