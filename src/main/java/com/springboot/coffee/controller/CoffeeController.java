package com.springboot.coffee.controller;

import com.springboot.coffee.dto.CoffeePostDto;
import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.mapper.CoffeeMapper;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/ver1/coffees")
@Validated
public class CoffeeController {
    private static final String MEMBER_DEFAULT_URL = "/ver1/coffees";
//    서비스계층과 매퍼 의존성주입
    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }


    //    post 요청에 따른 핸들러 메서드 구현
    @PostMapping
//    CoffeePostDto를 만들어 준다 필드값은 PostDto에서 설정해주겠다.
    private ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto) {
        Coffee postCoffee = coffeeService.createCoffee(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL,postCoffee.getCoffeeId());
        return ResponseEntity.created(location).build();
    }
}
