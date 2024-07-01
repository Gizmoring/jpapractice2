package com.springboot.member.entity;

import com.springboot.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(nullable = false,length = 10)
    private String name;
    @Column(nullable = false, unique = true,updatable = false)
    private String email;
    @Column(nullable = false,length = 13,unique = true)
    private String phone;
//  생성된날짜는 수정 불가
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false,name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();
    //    멤버는 주문들의 상태를 가지고 있어야 하므로 양방향 연결
//   현재 order가 참고하고있는 객체의 Member의 변수명은 member
//    똑같이 영속성 컨텍스트에서 객체안에 리스트들의 정보는 자동으로 업데이트 해주지 않기때문에
//    만드는 시점에 영속성전이를 해줌
    @OneToMany(mappedBy = "member",cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();
    public void setOrder(Order order){
//        주문이 생성될 떄 가져온 Member가 현재 Member가 아니라면
//        주문이 생성될 떄 의 Member를 호출하는 시점의 Member로 바꿔준다.

        orders.add(order);
        if(order.getMember()!= this){
            order.setMember(this);
        }

    }

    // 멤버가 생성될 떄 상태는 활동중
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),

        MEMBER_SLEEP("휴면중"),

        MEMBER_QUIT("탈퇴");

//        멤버의 상태가 만들어 질 때 문자열을 ()안에 상태로 만들어 주겠다.
        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

}
