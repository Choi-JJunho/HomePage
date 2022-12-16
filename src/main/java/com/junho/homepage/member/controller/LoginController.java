package com.junho.homepage.member.controller;

import com.junho.homepage.member.dto.SignUpRequest;
import com.junho.homepage.member.dto.TokenResponse;
import com.junho.homepage.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public ResponseEntity<TokenResponse> signin(@RequestBody SignUpRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignUpRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃", notes = "Header에 담겨있는 Token을 기준으로 로그아웃을 수행한다.")
    @PostMapping(value = "/signout")
    public ResponseEntity<Boolean> signout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(memberService.logout(authentication.getName()), HttpStatus.OK);
    }

    @ApiOperation(value = "토큰 재발급", notes = "refresh Token의 정보를 토대로 access 토큰 재발급")
    @PostMapping(value = "/refresh")
    public ResponseEntity<Boolean> refresh() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(memberService.logout(authentication.getName()), HttpStatus.OK);
    }
}