package com.example.project.controller;

import com.example.project.dto.RestaurantSaveDto;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final ResourceLoader resourceLoader;

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

    @PostMapping("/save")
    public String restaurantSave(@ModelAttribute @Validated RestaurantSaveDto restaurantSaveDto
                                 ,BindingResult bindingResult) throws Exception{

        ModelAndView mv = new ModelAndView();
        mv.setViewName("restaurant");

        if(bindingResult.hasErrors()){ // bindingResult에 걸리는거 없는 경우
            log.info("error={}", bindingResult);
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError errorMsg : fieldErrors) {
                System.out.println("errorMsgCode = " + errorMsg.getField());
                System.out.println("errorMsg = " + errorMsg.getDefaultMessage());
            }
            return "redirect:/restaurant"; // 나중에 등록폼으로 변경 해야 함
        }

        restaurantService.restaurantSave(restaurantSaveDto);



        return "redirect:/restaurant";
    }

    @GetMapping("/test")
    @ResponseBody
    public int test(){

        int count = restaurantService.getCount();

        log.info("count={}",count);

        return 200;
    }

    @PostMapping("/fileTest")
    @ResponseBody
    public void FileSaveTest(HttpServletRequest
            request, @RequestParam("img") MultipartFile file) throws IOException {

        Resource resource = resourceLoader.getResource("classpath:static");
        String staticPath = resource.getURL().getPath();

        //파일경로
        String filePath = staticPath + "/uploadFiles/";

        File uploadDir = new File(filePath);

        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        System.out.println("filePath = " + filePath);

        String originFileName = file.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        Long size = file.getSize();
        String fileSize = String.valueOf(size);
        String changeName = UUID.randomUUID().toString() + ext;

        //저장할 경로명에 바뀐파일이름 + 확장자 추가해서 넣어준다.
        file.transferTo(new File(filePath + "\\" + changeName));
    }




}
