package com.junho.homepage.member.service;

import com.junho.config.security.AuthorityType;
import com.junho.config.security.JwtProvider;
import com.junho.config.security.token.RefreshToken;
import com.junho.config.security.token.RefreshTokenRepository;
import com.junho.config.security.token.TokenResponse;
import com.junho.homepage.member.domain.Authority;
import com.junho.homepage.member.domain.Member;
import com.junho.homepage.member.dto.request.SignInRequest;
import com.junho.homepage.member.dto.request.SignUpRequest;
import com.junho.homepage.member.dto.response.MemberResponse;
import com.junho.homepage.member.mapper.MemberMapper;
import com.junho.homepage.member.repository.MemberRepository;
import com.junho.homepage.utils.AuthUtils;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public TokenResponse signIn(SignInRequest request) {
        // 아이디 검증
        Member member = memberRepository.findByAccount(request.getAccount())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_LOGIN_INFO));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_LOGIN_INFO);
        }

        String accessToken = jwtProvider.createToken(member.getAccount(), member.getRoles());
        refreshTokenRepository.save(RefreshToken.of(member.getAccount(), member.resetToken()));

        return TokenResponse.of(accessToken, member.getRefreshToken());

    }

    public boolean signUp(SignUpRequest request) {
        try {
            Member member = MemberMapper.INSTANCE.toMemberEntity(request);
            member.setRoles(Collections.singletonList(
                    Authority.builder()
                            .name(AuthorityType.ROLES.USER)
                            .build())
            );

            memberRepository.save(member);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_ACCOUNT);
        }
        return true;
    }

    public boolean signOut(String account) {
        refreshTokenRepository.deleteById(account);
        return true;
    }

    public MemberResponse getMember(String account) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    public TokenResponse refresh(String refreshToken) {
        Member member = AuthUtils.getCurrentMember();

        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new ApiException(ErrorCode.TOKEN_INVALID);
        }

        refreshTokenRepository.findById(member.getAccount())
                .orElseThrow(() -> new ApiException(ErrorCode.TOKEN_EXPIRED));

        String accessToken = jwtProvider.createToken(member.getAccount(), member.getRoles());

        return TokenResponse.from(accessToken);
    }
}
