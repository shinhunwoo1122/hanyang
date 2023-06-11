package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ResourceLoader resourceLoader;

    /**
     * @Method restaurant
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당페이지 조회
     */
    @GetMapping("")
    public ModelAndView restaurant(@RequestParam(defaultValue = "") String message
                                  ,@RequestParam(defaultValue = "0") Integer category){
        ModelAndView mView = new ModelAndView();
        List<RestaurantDto> list = restaurantService.getRestaurants(category);

        mView.setViewName("restaurant");
        if(message.equals("success")){
            mView.addObject("message","정상 등록 처리되었습니다.");
        }
        if(message.equals("edit")){
            mView.addObject("message","정상 수정 처리되었습니다.");
        }
        mView.addObject("list", list);

        return mView;
    }

    /**
     * @Method restaurantDetail
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 상세 페이지 조회
     */
    @GetMapping("/{id}")
    public ModelAndView restaurantDetail(@PathVariable Integer id){
        RestaurantDto restaurantDto = restaurantService.getRestaurant(id);
        restaurantDto.setFileUrl("../"+restaurantDto.getFileUrl());
        System.out.println("restaurantDto = " + restaurantDto);

        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant-detail");
        mView.addObject("restaurantDto", restaurantDto);

        return mView;
    }

    /**
     * @Method restaurantSaveForm
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 등록폼 이동
     */
    @GetMapping("/save-form")
    public ModelAndView restaurantSaveForm(){
        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant-save");
        mView.addObject("restaurantSaveDto",new RestaurantSaveDto());
        return mView;
    }

    /**
     * @Method restaurantSave
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 등록 기능 구현
     */
    @PostMapping("/save")
    public String restaurantSave(@ModelAttribute @Validated RestaurantSaveDto restaurantSaveDto
                                 , BindingResult bindingResult) throws Exception{

        //복합룰 적용
        if(!restaurantSaveDto.getPwd().equals(restaurantSaveDto.getPwdCheck())){
            bindingResult.addError(new ObjectError("passwordCheckError", "비밀번호가 일치하지 않습니다."));
        }

        if(restaurantSaveDto.getFile().getSize() == 0){
            bindingResult.addError(new ObjectError("imgNotEmpty", "썸네일 이미지를 등록해주세요."));
        }

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError errorMsg : fieldErrors) {
                log.info("errorMsgCode={}", errorMsg.getField());
                log.info("errorMsg={}", errorMsg.getDefaultMessage());
            }
            return "restaurant-save";
        }

        restaurantService.restaurantSave(restaurantSaveDto);
        return "redirect:/restaurant?message=success";
    }

    /**
     * @Method passwordCheck
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 수정, 삭제를 위한 비밀번호 확인 조회
     */
    @PostMapping("/password-check")
    @ResponseBody
    public ResponseEntity<?> passwordCheck(@ModelAttribute PasswordCheckDto passwordCheckDto){
        ResponseDto responseDto = restaurantService.passwordCheck(passwordCheckDto);

        return new ResponseEntity<>(new ResponseDto<>(responseDto.getStatus(), responseDto.getMessage(), responseDto.getData()), HttpStatus.OK);
    }

    /**
     * @Method restaurantEditForm
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 수정폼 이동
     */
    @GetMapping("/edit/{id}")
    public ModelAndView restaurantEditForm(@PathVariable Integer id){
        ModelAndView mView = new ModelAndView();
        mView.setViewName("restaurant-edit");
        RestaurantDto restaurantDto = restaurantService.getRestaurant(id);
        mView.addObject("restaurantEditDto", restaurantDto);
        return mView;
    }

    /**
     * @Method restaurantEdit
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 수정 기능 구현
     */
    @PostMapping("/edit/{id}")
    public String restaurantEdit(@PathVariable Integer id
                                ,@ModelAttribute @Validated RestaurantEditDto restaurantEditDto
                                ,BindingResult bindingResult) throws Exception{

        System.out.println("restaurantEditDto.getFile().getSize() = " + restaurantEditDto.getFile().getSize());
        
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError errorMsg : fieldErrors) {
                System.out.println("errorMsgCode = " + errorMsg.getField());
                System.out.println("errorMsg = " + errorMsg.getDefaultMessage());
            }
            return "redirect:/restaurant/edit/" + String.valueOf(id); // 나중에 등록폼으로 변경 해야 함
        }
        restaurantService.restaurantEdit(id,restaurantEditDto);

        return "redirect:/restaurant?message=edit";
    }

    /**
     * @Method deleteRestaurant
     * @Author: shinhunwoo(신헌우)
     * @Description: 식당 삭제 기능 구현
     */
    @PatchMapping("/delete-restaurant")
    public ResponseEntity<?> deleteRestaurant(@RequestParam Integer id){
        //row삭제는 하지 않고 상태 값 만 수정함
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(),"게시글 삭제 완료", id), HttpStatus.OK);
    }
}
