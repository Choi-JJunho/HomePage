package com.junho.homepage.member.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

    @ApiModelProperty(notes = "고유 id", example = "10")
    private Long id;

    @NotNull(message = "아이디는 비워둘 수 없습니다.")
    @Pattern(regexp = "^[a-z_0-9]{1,12}$", message = "아이디 형식이 올바르지 않습니다.")
    @ApiModelProperty(notes = "학교 계정 아이디", example = "junho5336")
    private String account;

    @NotNull(message = "비밀번호는 비워둘 수 없습니다.")
    // TODO : 비밀번호 자리수에 대한 규정
    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "최소 8자, 하나 이상의 문자와 하나의 숫자를 가져야합니다")
    @ApiModelProperty(notes = "비밀번호", example = "12341234")
    private String password;

    @Size(max = 10, message = "닉네임은 10자 이내여야 합니다.")
    @ApiModelProperty(notes = "닉네임", example = "주노")
    private String nickname;

    @ApiModelProperty(notes = "익명 닉네임", example = "익명_1522771686642")
    private String anonymous_nickname;

    @Size(max = 50, message = "이름은 50자 이내여야 합니다.")
    @ApiModelProperty(notes = "이름", example = "최준호")
    private String name;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "이메일 형식이 올바르지 않습니다.")
    @ApiModelProperty(notes = "이름", example = "최준호")
    private String email;
}
