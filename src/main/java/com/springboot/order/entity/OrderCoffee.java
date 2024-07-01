package com.springboot.order.entity;

import com.springboot.coffee.entity.Coffee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// ORDER와 COFFEE의 조인테이블 인 ORDER_COFFEE에서 ORRDER는 커피의 정보를 받아 갈 수 있도록 양방향 연결을 할 것이고
// 여기에서 수량을 확인할 수 있도록 해주겠다.
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ORDER_COFFEE")
public class OrderCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderCoffeeId;

//    다인쪽에서 무조건 매핑 해줘야함
//    외래키를 받아올 JoinColumn
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    Order order;

    public void setOrder(Order order){
        this.order = order;
//        주문에서 가져온 주문된커피정보들의 리스트안에서의 주문이 없다면
//        현재의 주문을 현재 주문된 커피정보로 바꿔준다.
        if(!order.getOrderCoffees().contains(this)){
            order.setOrderCoffee(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    Coffee coffee;

    @Column(nullable = false)
    private int quantity;
}
