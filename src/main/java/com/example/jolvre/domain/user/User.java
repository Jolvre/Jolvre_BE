package com.example.jolvre.domain.user;

import com.example.jolvre.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private String password;

    @Column
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column
    private String city;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String school;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;
    private String refreshToken;

    @Builder
    public User(String password, String name, String nickname, int age, String city, String email, String imageUrl,
                Role role, String school, SocialType socialType, String socialId, String refreshToken) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.city = city;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.school = school;
        this.socialType = socialType;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
    }

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void authorizeStudent() {
        this.role = Role.STUDENT;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
