package com.its.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp // 작성된 시간을 나타내 준다.
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp // 수정된 시간을 나타내 준다.
    @Column(insertable = false)
    private  LocalDateTime updatedTime;
}
