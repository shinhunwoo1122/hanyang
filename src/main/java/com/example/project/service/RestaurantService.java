package com.example.project.service;

import com.example.project.dto.RestaurantSaveDto;

public interface RestaurantService {
    int getCount();
    boolean restaurantSave(RestaurantSaveDto restaurantSaveDto);
}
