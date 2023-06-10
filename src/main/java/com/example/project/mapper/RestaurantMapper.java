package com.example.project.mapper;

import com.example.project.dto.FileDto;
import com.example.project.dto.RestaurantDto;
import com.example.project.dto.RestaurantEditDto;
import com.example.project.dto.RestaurantSaveDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    int getCount();
    void restaurantSave(RestaurantSaveDto restaurantSaveDto);
    void restaurantImgSave(FileDto fileDto);
    RestaurantDto getRestaurant(Integer id);
    String getEncodePwd(Integer id);
    void fileEditStatus(Integer id);
    void restaurantEdit(RestaurantEditDto restaurantEditDto);
    List<RestaurantDto> getRestaurants(Integer category);
}
