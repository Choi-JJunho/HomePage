package com.junho.homepage.member.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @ApiModelProperty(notes = "학교 계정 아이디", example = "junho5336")
    private String account;

    @ApiModelProperty(notes = "비밀번호", example = "abcd1234")
    private String password;
}
