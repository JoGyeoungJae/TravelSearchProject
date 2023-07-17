package com.trInfo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {
    // @NotBlank - NULL체크 + 문자열 길이 0인지 검사 + 빈 문자열("")인지 검사
    // @NotEmpty - NULL체크 + 문자열 길이 0인지 검사
    // @Null - 값이 NULL인지 검사
    // @NotNull - 값이 NULL이 아닌지 검사


    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String tel;
}



