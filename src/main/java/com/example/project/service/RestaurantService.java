package com.example.project.service;

import com.example.project.dto.*;

import java.util.List;

public interface RestaurantService {

    void restaurantSave(RestaurantSaveDto restaurantSaveDto) throws Exception;
    RestaurantDto getRestaurant(Integer id);
    void restaurantEdit(Integer id, RestaurantEditDto restaurantEditDto) throws Exception;
    List<RestaurantDto> getRestaurants(Integer category);
    ResponseDto passwordCheck(PasswordCheckDto passwordCheckDto);
    void deleteRestaurant(Integer id);
}
