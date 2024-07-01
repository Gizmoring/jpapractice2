package com.springboot.coffee.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


//@Valid 스프링에서 하는게 아님? 자바에서 하는건가.
//@Validated 는 스프링에서 해주는건가.
@Getter
public class CoffeePostDto {
    @NotBlank
    private String korName;

    @NotBlank
    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
            message = "커피명(영문)은 영문이어야 합니다(단어 사이 공백 한 칸 포함). 예) Cafe Latte")
    private String engName;

    @Range(min= 100, max= 50000)
    private int price;
    @NotBlank
    @Pattern(regexp = "^([A-Za-z]){3}$",
            message = "커피 코드는 3자리 영문이어야 합니다.")
    private String coffeeCode;
}
