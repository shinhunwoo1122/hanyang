package com.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper {
    int getCount();
}
