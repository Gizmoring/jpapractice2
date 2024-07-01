package com.springboot.member.repository;

import com.springboot.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// class로 생성했으나 interface로 변경
// JpaRepository<> 를 상속받았고 T에 Member 클래스(entity)와 Long 타입 memberId를 매개변수로 받겠다
// 처음 long 원시타입 -> Long 참조타입 제네릭안에는 원시타입이 들어갈 수 없었다.
// Optional 클래스를 사용할 수 없다 -> import java.util.Optional
// Optioanl 클래스를 사용한 이유는 email을 제외하여 필드값에 null이 들어 올 수도 있음을 명시해뒀다.
// 서비스 계층에서 email 검증을 위해 DB에서 Email을 찾아 올 메서드 override JpaRepository안에 추상회 되어있음
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
