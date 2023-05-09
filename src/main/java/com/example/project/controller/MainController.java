package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView Main(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("index");

        return mView;
    }

    @GetMapping("/1")
    public ModelAndView contact(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("contact");

        return mView;
    }

    @GetMapping("/2")
    public ModelAndView about(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("about");

        return mView;
    }

    @GetMapping("/3")
    public ModelAndView shop(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("shop");

        return mView;
    }

    @GetMapping("/4")
    public ModelAndView single(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("shop-single");

        return mView;
    }

    @GetMapping("/5")
    public ModelAndView contentDetail(){

        ModelAndView mView = new ModelAndView();
        mView.setViewName("content-detail");

        return mView;
    }

}
