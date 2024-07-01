package com.springboot.coffee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coffeeId;
    @Column(nullable = false)
    private String korName;
    @Column(nullable = false)
    private String engName;
//    커피의 식별자는 커피코드.
    @Column(nullable = false,unique = true, length = 3)
    private String coffeeCode;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false,name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();
//    판매에 대한 상태도 넣어줘야함 내부클래스로 만들어 주고 커피가 생성될 떄는 판매중을 기본값으로 설정하겠다.
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CoffeeStatus coffeeStatus = CoffeeStatus.COFFEE_FOR_SALE;

    public enum CoffeeStatus{
        COFFEE_FOR_SALE("판매중"),
        COFFEE_PAUSE("품절"),
        COFFEE_NOT_SALE("판매중지");

        @Getter
        private String Status;

        CoffeeStatus(String status) {
            this.Status = status;
        }
    }


}
