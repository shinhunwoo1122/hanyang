package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping({"/","/main"})
    public ModelAndView Main(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("main");

        return mView;
    }

    @GetMapping("/1")
    public ModelAndView contact(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("community");

        return mView;
    }

    @GetMapping("/2")
    public ModelAndView about(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("index");

        return mView;
    }

    @GetMapping("/3")
    public ModelAndView shop(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant");

        return mView;
    }

    @GetMapping("/4")
    public ModelAndView single(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant-detail");

        return mView;
    }

    @GetMapping("/5")
    public ModelAndView contentDetail(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("community-detail");

        return mView;
    }

}
