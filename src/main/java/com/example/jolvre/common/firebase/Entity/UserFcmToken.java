package com.example.jolvre.common.firebase.Entity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class UserFcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFcmTokenId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    @Builder
    public UserFcmToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
