package com.simplegram.repository;

import com.simplegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByLogin(String login);
    Boolean existsByUsername(String username);
    Boolean existsByLogin(String login);

    @Modifying
    @Transactional
    @Query("update User user set user.avatar =:avatar where user.id =:UUID")
    void updateAvatarByUUID(@Param("UUID") String userUUID, @Param("avatar") String newAvatar);
}