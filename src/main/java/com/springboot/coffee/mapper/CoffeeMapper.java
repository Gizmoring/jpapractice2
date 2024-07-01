package com.springboot.coffee.mapper;

import com.springboot.coffee.dto.CoffeePostDto;
import com.springboot.coffee.entity.Coffee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    Coffee coffeePostDtoToCoffee(CoffeePostDto coffeePostDto);
}
