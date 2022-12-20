package com.junho.homepage.member.service;

import com.junho.config.security.JwtProvider;
import com.junho.config.security.token.AccessToken;
import com.junho.config.security.token.AccessTokenRepository;
import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.ApiException;
import com.junho.homepage.member.Authority;
import com.junho.homepage.member.Member;
import com.junho.homepage.member.dto.MemberResponse;
import com.junho.homepage.member.dto.SignUpRequest;
import com.junho.homepage.member.dto.TokenResponse;
import com.junho.homepage.member.mapper.MemberMapper;
import com.junho.homepage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private static final String tokenWhiteList = "tokenWhiteList";
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final JwtProvider jwtProvider;

    public TokenResponse signIn(SignUpRequest request) {
        // 아이디 검증
        Member member = memberRepository.findByAccount(request.getAccount())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_LOGIN_INFO));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_LOGIN_INFO);
        }

        String accessToken = jwtProvider.createAccessToken(member.getAccount(), member.getRoles());
        accessTokenRepository.save(AccessToken.of(member.getAccount(), accessToken));
        return TokenResponse.of(accessToken, member.getRefreshToken());

    }

    public boolean signUp(SignUpRequest request) {
        try {
            Member member = MemberMapper.INSTANCE.toMemberEntity(request);
            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_MANAGER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_ACCOUNT);
        }
        return true;
    }

    public boolean signOut(String account) {
        accessTokenRepository.deleteById(account);
        return true;
    }

    public MemberResponse getMember(String account) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    public TokenResponse refresh(String refreshToken) {


        return null;
    }
}
