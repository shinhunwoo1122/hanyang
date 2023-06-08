package com.example.project.mapper;

import com.example.project.dto.RestaurantSaveDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper {
    int getCount();

    boolean restaurantSave(RestaurantSaveDto restaurantSaveDto);
}
