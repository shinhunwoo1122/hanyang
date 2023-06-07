package com.example.project.controller;

import com.example.project.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("")
    public ModelAndView community(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("community");

        return mView;
    }

    @GetMapping("/{id}")
    public ModelAndView communityDetail(@PathVariable Integer id){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("community-detail");

        return mView;
    }



}
