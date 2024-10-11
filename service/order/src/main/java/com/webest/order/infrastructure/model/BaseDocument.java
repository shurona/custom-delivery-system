package com.webest.order.infrastructure.model;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
public abstract class BaseDocument {

    @CreatedDate
    @Field("created_at")  // MongoDB에서 필드 이름을 스네이크 케이스로 저장할 경우
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    protected Long createdBy;

    @LastModifiedDate
    @Field("updated_at")
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    protected Long updatedBy;

    @Field("is_deleted")
    protected Boolean isDeleted = false;  // 논리적 삭제 처리


}
