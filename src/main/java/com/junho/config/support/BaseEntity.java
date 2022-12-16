package com.junho.config.support;

import com.junho.homepage.member.Member;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
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
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDate;

    /**
     * 갱신일시
     */
    @LastModifiedDate
    @Column(name = "update_dt")
    private LocalDateTime updateDate;

    @CreatedBy
    @OneToOne
    @JoinColumn(name = "crtd_by")
    private Member creator;

    @LastModifiedBy
    @OneToOne
    @JoinColumn(name = "updt_by")
    private Member modifier;

}
