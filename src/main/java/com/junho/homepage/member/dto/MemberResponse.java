package com.junho.homepage.member.dto;

import com.junho.homepage.member.Authority;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    @ApiModelProperty(notes = "고유 id", example = "10")
    private Long id;

    @ApiModelProperty(notes = "학교 계정 아이디", example = "junho5336")
    private String account;

    @ApiModelProperty(notes = "닉네임", example = "주노")
    private String nickname;

    @ApiModelProperty(notes = "이름", example = "최준호")
    private String name;

    @ApiModelProperty(notes = "이메일", example = "junho5336@gmail.com")
    private String email;
    
    @ApiModelProperty(notes = "권한 목록", example = "ROLE_USER")
    private List<Authority> roles;
}
