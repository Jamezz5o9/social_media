package com.prophius.socialMedia.notification;

import com.prophius.socialMedia.audit.DateAudit;
import com.prophius.socialMedia.post.Post;
import com.prophius.socialMedia.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Notification extends DateAudit {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", insertable = false, updatable = false, nullable = false)
    private String id;

    private String type;

    private String content;

    @ManyToOne
    private AppUser targetUser;

    @ManyToOne
    private Post relatedPost;

}
