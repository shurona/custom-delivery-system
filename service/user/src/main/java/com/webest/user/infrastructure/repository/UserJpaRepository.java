package com.webest.user.infrastructure.repository;

import com.webest.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);

    @Modifying
    @Query("UPDATE User s SET  s.isDeleted = true WHERE s.userId = :userId")
    void delete(@Param("userId") String userId);
}
