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
@Table(name = "chatroom")
@Getter
@SQLRestriction("is_deleted = false")
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User roomOwner; // 가게 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User participant; // 참가자

    // jpa를 위한 기본생성자
    protected ChatRoom() {
    }

    // Private 생성자
    private ChatRoom(Long id, String content, User owner, User participant) {
        this.id = id;
        this.content = content;
        this.roomOwner = owner;
        this.participant = participant;
    }

    // Public Static Factory Method
    public static ChatRoom from(String content, User owner, User participant) {
        return new ChatRoom(null, content, owner, participant);
    }
}
