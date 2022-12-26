package com.junho.homepage.member.repository;

import com.junho.homepage.member.dto.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryExtension {
    Page<MemberResponse> findAll(String keyword, Pageable pageable);
}
