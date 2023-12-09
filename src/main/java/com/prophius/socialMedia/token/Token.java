package com.prophius.socialMedia.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prophius.socialMedia.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;


import java.time.LocalDateTime;

import static com.prophius.socialMedia.utils.ConstantUtils.EXPIRATION;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Token extends RepresentationModel<Token> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", insertable = false, updatable = false, nullable = false)
    private String id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = AppUser.class)
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser user;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    private LocalDateTime expiryDate;

    private String tokenType;

    public Token(String token, AppUser user, String tokenType) {
        this.token = token;
        this.user = user;
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private LocalDateTime calculateExpiryDate(long expiryTimeInHours){
        return LocalDateTime.now().plusHours(expiryTimeInHours);
    }

    public void updateToken(String code, String tokenType){
        this.token = code;
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}

