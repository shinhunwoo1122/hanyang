package com.example.project.service;

import com.example.project.dto.RestaurantDto;
import com.example.project.dto.RestaurantEditDto;
import com.example.project.dto.RestaurantSaveDto;

public interface RestaurantService {
    int getCount();
    void restaurantSave(RestaurantSaveDto restaurantSaveDto) throws Exception;
    RestaurantDto getRestaurant(Integer id);

    boolean passwordCheck(Integer id, String pwd);

    void restaurantEdit(RestaurantEditDto restaurantEditDto) throws Exception;
}
