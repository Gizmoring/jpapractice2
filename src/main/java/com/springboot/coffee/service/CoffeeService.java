package com.springboot.coffee.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.repository.CoffeeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

//    커피가 있는지 없는지 검증부터 - 지금은 커피코드가 식별자.
    public Coffee createCoffee(Coffee coffee){
        verifiedCoffeeCode(coffee.getCoffeeCode());
        return coffeeRepository.save(coffee);
    }

    public void verifiedCoffeeCode(String coffeeCode){
        Optional<Coffee> Coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if(Coffee.isPresent()){
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }
}
