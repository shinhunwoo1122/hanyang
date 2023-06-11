package com.example.project.mapper;

import com.example.project.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    void restaurantSave(RestaurantSaveDto restaurantSaveDto);
    void restaurantImgSave(FileDto fileDto);
    RestaurantDto getRestaurant(Integer id);
    String getEncodePwd(PasswordCheckDto passwordCheckDto);
    void fileEditStatus(Integer id);
    void restaurantEdit(RestaurantEditDto restaurantEditDto);
    List<RestaurantDto> getRestaurants(Integer category);
    void deleteRestaurant(Integer id);
}
