package com.webest.user.infrastructure.repository;

import com.webest.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserName(String userName);

    @Modifying
    @Query("UPDATE User s SET s.isDeleted = true WHERE s.id = :userId")
    void delete(@Param("userId") Long userId);


}
