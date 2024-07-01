package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// @Service 에너테이션을 달아서 Service 계층임을 스프링에게 알려줌.
@Service
public class MemberService {
    //    서비스 계층은 Repository 없이는 동작할 수 없기에 생성자로 의존성 주입.
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    PostDto 를 받아서 entity 화 하고 비즈니스로직을 처리 현재 Post에 대한 요청을 받았다
//    public Member createMember(MemberPostDto memberPostDto) 처음 작성 -> 변경//
//    서비스 계층의 메서드 createMember()는 memberPostDto -> member entity 변환된 멤버를 받아서 로직을 처리하고
//    response로 나갈 Member Entity를 반환한다 컨트롤러 계층에서 mapper로 MemberResponseDto 로 변환해줄것.
//    멤버 Post요청을 처리할 때 현재 식별자로 email(unique)을 설정 해뒀기 때문에 검증이 필요.
//    email이 존재하는지 검증하는 메서드 verifyExistEmail() 를 작성하고 그곳에서 예외 처리를 같이 해주겠다.
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
//        통과하면 DB에 없는 email이므로 계정을 생성하고 DB에 저장
        return memberRepository.save(member);
    }

//    회원 가입을 위한 memberPostDto -> member entity의 email을 검증하는 메서드 작성
//   그런데 MemberEntity의 필드값 name email phone에는 전부 nullable false 인데 ?  post할때 null값 넣어도 들어오는구나..
//    Optional 클래스로 사용하는 이유 -> Post 요청에서 null값이 들어올 수도 있기 때문 -> all생성자로 넣어도 되나..?
//    예외를 던져 줘야 하는데 현재 능력으로는 할 수 없기때문에 예외관련 패키지들을 복사 해뒀다.
//    response 는 계층상관없이 사용할 수 있도록 제네릭으로 구현해 두겠다.
    public void verifyExistEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }

    }
}
