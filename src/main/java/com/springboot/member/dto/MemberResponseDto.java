package com.springboot.member.dto;

import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

//요청을 처리하고 클라이언트에 보내줄 응답객체 responseDto
//선생님께서 최대한 예쁘게 잘 담아서 보내주라고 말씀하심.
@Getter
@Setter
public class MemberResponseDto {
    private String name;
    private String email;
    private String phone;
    private Member.MemberStatus memberStatus;
    private LocalDateTime createdAt;

//    response를 보내 줄 때 MemberStatus 안에 있는 String status를 가져오기 위해 메서드 작성
    public String getMemberStatus(){
        return memberStatus.getStatus();
    }
}
