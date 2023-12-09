package com.prophius.socialMedia.user;

import com.prophius.socialMedia.audit.DateAudit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "app_user")
public class AppUser extends DateAudit {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", insertable = false, updatable = false, nullable = false)
    private String id;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profilePictureUrl;

    @ManyToMany
    private Set<AppUser> followers;

    @ManyToMany
    private Set<AppUser> following;

    private boolean isVerified;
    private String role;
}
