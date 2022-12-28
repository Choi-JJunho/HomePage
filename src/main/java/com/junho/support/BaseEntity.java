package com.junho.support;

import com.junho.homepage.member.Member;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * 등록일시, 갱신일시, 삭제여부
 * 동일한 패턴으로 사용되는 엔티티 상속으로 사용
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 등록일시
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    /**
     * 갱신일시
     */
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 작성자
     */
    @CreatedBy
    @OneToOne
    private Member creator;

    /**
     * 수정자
     */
    @LastModifiedBy
    @OneToOne
    private Member modifier;

    /**
     * Soft Delete 용
     */
    private boolean enabled = true;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
