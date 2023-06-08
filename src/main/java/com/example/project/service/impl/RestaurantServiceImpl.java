package com.example.project.service.impl;

import com.example.project.dto.RestaurantSaveDto;
import com.example.project.mapper.RestaurantMapper;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public int getCount() {

        int count = restaurantMapper.getCount();

        String pwd = "1234G";
        String encodePwd = passwordEncoder.encode(pwd);

        log.info("encodingPwd={}", encodePwd);
        log.info("비밀번호확인={}", passwordEncoder.matches(pwd,encodePwd));
        log.info("비밀번호일부러틀리게={}",passwordEncoder.matches("1234C",encodePwd));

        return 0;
    }

    @Override
    @Transactional
    public boolean restaurantSave(RestaurantSaveDto restaurantSaveDto) {

        boolean isSuccess = restaurantMapper.restaurantSave(restaurantSaveDto);

        log.info("isSuccess={}",isSuccess);

        return isSuccess;

    }
}
