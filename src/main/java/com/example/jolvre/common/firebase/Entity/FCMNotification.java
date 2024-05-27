package com.example.jolvre.common.firebase.Entity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class FCMNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    @Builder
    public FCMNotification(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public void confirmUser(User user) {
        this.user = user;
    }
}
