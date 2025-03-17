package com.webest.user.infrastructure.repository;

import com.webest.user.domain.chat.ChatRoom;
import com.webest.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatJpaRepository extends JpaRepository<ChatRoom, Long> {

    // 주인과 참가자로 조회
    Optional<ChatRoom> findByRoomOwnerAndParticipant(User owner, User participant);
}
