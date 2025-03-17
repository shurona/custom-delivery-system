package com.webest.user.domain.chat;

import com.webest.app.jpa.BaseEntity;
import com.webest.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "chat_logs")
@Getter
@SQLRestriction("is_deleted = false")
public class ChatLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    // jpa를 위한 생성자
    protected ChatLog() {
    }

    private ChatLog(Long id, String content, ChatRoom room, User writer) {
        this.id = id;
        this.content = content;
        this.room = room;
        this.writer = writer;
    }

    public static ChatLog from(String content, ChatRoom room, User writer) {
        return new ChatLog(
            null, content, room, writer
        );
    }

}
