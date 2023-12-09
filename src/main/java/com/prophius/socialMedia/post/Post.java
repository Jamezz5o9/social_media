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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", insertable = false, updatable = false, nullable = false)
    private String id;

    private String content;
    private LocalDateTime creationDate;
    private int likesCount;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @ManyToMany
    private Set<AppUser> likedBy;
}
