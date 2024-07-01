package com.springboot.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
// client에서 post 요청이 들어 올 때 필요한 값들을 필드로 가지고 있어야 한다
public class MemberPostDto {
    @NotBlank (message = "이름엔 공백이 들어올 수 없습니다")
    private String name;

    @NotBlank
//    @Email e메일 스펙만 가능하게 설저
    @Email
    private String email;

//    @Pattern(regexp = ) 정규표현식을 이용하여 검증을하고 메시지를 작성해둠
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String phone;
}
