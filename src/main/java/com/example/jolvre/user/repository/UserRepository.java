package com.example.jolvre.user.repository;

import com.example.jolvre.user.entity.SocialType;
import com.example.jolvre.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
