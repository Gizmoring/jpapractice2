package com.springboot.member.mapper;


import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;

//현재 PostDto -> Entity로 변경하는 매퍼를 구현하러 왔다.
//그리고 스프링컨테이너에게 Mapper임을 알려주도록 하는 에너테이션을 달아 줘야한다.
// 그냥은 안되네...
//	implementation 'org.mapstruct:mapstruct:1.4.2.Final'
//	annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final' 디펜던시와 import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  Member memberPostDtoToMember(MemberPostDto memberPostDto);

  MemberResponseDto memberToMemberResponseDto(Member member);

}
