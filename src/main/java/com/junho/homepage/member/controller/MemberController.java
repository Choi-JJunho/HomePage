package com.junho.homepage.member.controller;

import com.junho.homepage.member.dto.response.MemberResponse;
import com.junho.homepage.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/users")
    public ResponseEntity<Page<MemberResponse>> getUsers(String keyword, @PageableDefault Pageable pageable) {

        return new ResponseEntity<>(memberService.getMembers(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<MemberResponse> getUser(@RequestParam String name) {
        
        return new ResponseEntity<>(memberService.getMember(name), HttpStatus.OK);
    }
}
