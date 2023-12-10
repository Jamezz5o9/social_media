package com.prophius.socialMedia.post;

import com.prophius.socialMedia.audit.DateAudit;
import com.prophius.socialMedia.comment.Comment;
import com.prophius.socialMedia.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Post extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int likesCount;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> likedBy;

}
