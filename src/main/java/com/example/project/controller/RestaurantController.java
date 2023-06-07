package com.example.project.controller;

import com.example.project.dto.RestaurantSaveDto;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;


    @GetMapping("")
    public ModelAndView restaurant(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant");

        return mView;
    }

    @GetMapping("/{id}")
    public ModelAndView restaurantDetail(@PathVariable Integer id){

        log.info("id={}", id);

        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant-detail");

        return mView;
    }

    @GetMapping("/test")
    @ResponseBody
    public int test(){

        int count = restaurantService.getCount();

        log.info("count={}",count);

        return 200;
    }

    /*@PostMapping("/save")
    @ResponseBody
    public Integer save(@ModelAttribute RestaurantSaveDto restaurantSaveDto){


        //return new ResponseEntity<>(new ResponseDto);

    }*/



}
