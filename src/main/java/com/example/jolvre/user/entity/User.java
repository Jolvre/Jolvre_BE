package com.example.jolvre.user.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.group.entity.Member;
import com.example.jolvre.user.dto.UserDTO.UserUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Setter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private int age;

    @Column
    private String city;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String imageUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String school;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Member> members;

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

    public User update(UserUpdateRequest user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.city = user.getCity();

        return this;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
