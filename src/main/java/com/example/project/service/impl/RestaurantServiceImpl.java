package com.example.project.service.impl;

import com.example.project.mapper.RestaurantMapper;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    @Override
    public int getCount() {

        int count = restaurantMapper.getCount();

        return 0;
    }
}
