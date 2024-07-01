package com.springboot.member.controller;

import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.entity.Member;
import com.springboot.member.mapper.MemberMapper;
import com.springboot.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.utils.UriCreator;
import javax.validation.Valid;
import java.net.URI;

// @RestController 컨트롤러임을 설정
@RestController
// ()안에 url로 오는 요청에 대해 이 컨트롤러로 매핑하겠다.
@RequestMapping("/ver1/members")
// 검증도 함께 하겠다. (스프링 컨테이너야 여기 유효성 검증 해 "줘" ) 검증에 대한 로직은 advice에 @RestControllerAdvice에 있다
@Validated
@Slf4j
public class MemberController {
    //    컨트롤러 안에 필드값으로 필요한 것들을 생각해보자.
//    개선하면서 uri 만들어 주기위해 초기 Url값 할당해줬다.
    private static final String MEMBER_DEFAULT_URL = "/ver1/members";

    private final MemberService memberService;

    private final MemberMapper membermapper;
    @Autowired

    public MemberController(MemberService memberService, MemberMapper membermapper) {
        this.memberService = memberService;
        this.membermapper = membermapper;
    }

    //    /ver1/members 로 들어오는 post에 대한 요청을 이 핸들러에 매핑해주겠다.
//    검증을 위한 @Valid 사용을 위해 디펜던시를 추가해줌
//    dependency 추가
//    implementation 'org.springframework.boot:spring-boot-starter-validation'
//    만들다 보니 PostDto -> Entity로 바꿔줄 매퍼가 필요하다. mapper를 구현하러 가보자.
//    구현하고오니 mapper가 없으면 controller는 동작하지 않아야한다. -> 의존성주입

    //    여기까지 생성해보고 postman 으로 생성하고오자 could not send request 에러가 뜬다
//    api계층에서 service계층까지 전달이 안되고 있다 필드값에 mapper만 의존성주입해둔게 문제인 것 같다 service도 의존성 주입해주자.
//    다시 postman 으로 요청했으나 제대로 요청이 안간다. 왜 일까 보니 memberService를 사용하지 않고 있었다. (1)로 가보자
    @PostMapping
    private ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
//     서비스계층으로 보낼 postMember 엔티티를 매퍼를 사용하여 매핑시켜준다.
//        (1) 처음 작성한 Member postMember = mapper.PostMemberDtoToMember(memberPostDto); 는 postDto를 member Entity로
//             변환만 시켜준 상태였다 미리 작성해둔 service계층의 createMember(Member member)메서드를 실행하자.. 그리고 다시 postMan
        Member postMember = memberService.createMember(membermapper.memberPostDtoToMember(memberPostDto));
//     새로 등록되는 멤버의 uri를 생성하고 ResponseEntity를 통해서 http 201 상태코드를 반환하겠다.
//        location header에 생성된 회원 uri를 담아 줌
//        10분 이상을 헤메였다.. UriCreater라는 유틸 클래스를 작성해 줬어야 한다..

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL,postMember.getMemberId());
        return ResponseEntity.created(location).build();


//        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(postMember)),HttpStatus.CREATED);
    }
}
