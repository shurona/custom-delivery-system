package com.webest.auth.infrastructure.repository;

import com.webest.auth.domain.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthJpaRepository extends JpaRepository<Auth,Long> {

    Optional<Auth> findByUserId(String username);

}
