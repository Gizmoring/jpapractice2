package com.springboot.order.entity;


import com.springboot.coffee.entity.Coffee;
import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ORDERS")
//@Table(name = "ORDERS") 테이블이름은 상관없구나.
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    //    주문 상태 주문이 만들어지면 주문요청을 기본값으로 하겠다.
    @Enumerated(EnumType.STRING)
    private OrderStatus orderstatus = OrderStatus.ORDER_REQUEST;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();
    // OrderCoffee에서 coffee에 정보를 가져와야 하므로 양방향연결
//    1인쪽에 다인쪽의 정보를 list로 가지고 있어야한다
//    그리고 주문이 만들어 질 때 orderCoffee 가 주문하는 본인으로 만들어 줘야함
//    jpa는 영속성컨텍스트에서 객체 안에 있는 리스트의 정보들까지 자동으로 해줄 수 없기때문에 영속성전이를 해줘야한다.
//    생성할 때에만 전이 해주겠다.
    @OneToMany(mappedBy = "order",cascade = CascadeType.PERSIST)
//            = new ArrayList<> 를 추가해줘야한다 주문 할 때 생성자로 초기화 시켜주기 위함.
    List<OrderCoffee> orderCoffees = new ArrayList<>();
    // 주문안에 주문된 커피의 정보가 리스트로 담겨있고 Order 와 OrderCoffee 를 연결시켜줘야한다.
    public void setOrderCoffee(OrderCoffee orderCoffee) {
//        주문안에 담고 있는 주문된커피 리스트들에서 주문된 커피를 추가해준다.
//        그 주문된 커피정보에서 주문을 가져왔을 때 만들 당시의 주문이 아니라면
//        주문된 커피정보의 주문을 현재의 주문으로 바꿔준다
        orderCoffees.add(orderCoffee);
        if (orderCoffee.getOrder() != this) {
            orderCoffee.setOrder(this);
        }
    }
//    Order와 Member의 관계도 생각해봐야한다. n : 1 관계로 설정
//    하나의 주문은 한명의 회원에 귀속되고 한명의 멤버는 여러 주문을 가질 수 있음
//    다인쪽에서 1인쪽으로 먼저 이어줘야함.
//    @ManyToOne -> @JoinColumn 필수
//    @OneToMany -> mappedBy
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void setMember(Member member){
//        member에서 가져온 주문들에서 현재 만드는 시점의 주문이 없다면 현재 시점의 주문을 추가해 준다
        if(!member.getOrders().contains(this)){
            member.setOrder(this);
        }
        this.member =member;
    }



    // 하나의 주문 안에는 여러개의 커피를 가지고 있을 수 있잖아. -> 개수까지 포함해서 orderCoffee에넣어주자. 그걸 리스트로 받음 되겠다.
    public enum OrderStatus {
        ORDER_REQUEST(1, "주문요청"),
        //        확인 상태부터는 주문 취소가 불가능 -> 확인이 됐다는 건 확인 후 조리를 시작한다라고 설계하겠음.
        ORDER_CONFIRM(2, "주문확인"),
        ORDER_COMPLETE(3, "주문완료"),
        ORDER_CANCEL(4, "주문취소");

        @Getter
        private int statusNum;
        @Getter
        private String statusDescription;

        OrderStatus(int statusNum, String statusDescription) {
            this.statusNum = statusNum;
            this.statusDescription = statusDescription;
        }
    }
}
