package com.junho.homepage.member.controller;

import com.junho.homepage.annotation.RoleAdmin;
import com.junho.homepage.annotation.RoleManager;
import com.junho.homepage.annotation.RoleUser;
import com.junho.homepage.member.dto.MemberResponse;
import com.junho.homepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user")
    public ResponseEntity<MemberResponse> getUser(@RequestParam String account) {
        return new ResponseEntity<>(memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/user1")
    @RoleUser
    public ResponseEntity<MemberResponse> getUserFoUser(@RequestParam String account) {
        return new ResponseEntity<>(memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/user2")
    @RoleManager
    public ResponseEntity<MemberResponse> getUserForManager(@RequestParam String account) {
        return new ResponseEntity<>(memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/user3")
    @RoleAdmin
    public ResponseEntity<MemberResponse> getUserForAdmin(@RequestParam String account) {
        return new ResponseEntity<>(memberService.getMember(account), HttpStatus.OK);
    }
}
