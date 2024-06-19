//package com.example.jolvre.common.firebase.Repository;
//import com.example.jolvre.common.firebase.Entity.UserFcmToken;
//import com.example.jolvre.user.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, Long> {
//    Optional<UserFcmToken> findByUser(User user);
//    boolean existsByUser(User user);
//}