package com.example.project.mapper;

import com.example.project.dto.FileDto;
import com.example.project.dto.RestaurantDto;
import com.example.project.dto.RestaurantSaveDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper {
    int getCount();
    void restaurantSave(RestaurantSaveDto restaurantSaveDto);
    void restaurantImgSave(FileDto fileDto);
    RestaurantDto getRestaurant(Integer id);
    String getEncodePwd(Integer id);
}
