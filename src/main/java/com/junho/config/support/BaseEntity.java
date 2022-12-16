package com.junho.config.support;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 등록일시, 갱신일시, 삭제여부
 * 동일한 패턴으로 사용되는 엔티티 상속으로 사용
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    /**
     * 등록일시
     */
    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDate;

    /**
     * 갱신일시
     */
    @LastModifiedDate
    @Column(name = "update_dt")
    private LocalDateTime updateDate;

    @Column(name = "enbl_yn")
    private boolean enabled = true;
}
