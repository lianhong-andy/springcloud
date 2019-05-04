package com.andy.controller;

import com.andy.service.UserService;
import com.andy.user.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getUsers(){
        return userService.queryAll();
    }

    @RequestMapping("/add")
    public String addUser(String t){
        ObjectMapper mapper = new ObjectMapper();
        if(StringUtils.isBlank(t)) return "入参为空!";
        String result = "success";
        try {
            User user = mapper.readValue(t, User.class);
            log.info("user:{}",user);
            userService.addUser(user);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("类型转换异常：{}",e.getMessage(),e);
            result = "fail"+e.getMessage();
        }
        return result;

    }

    @RequestMapping("/addUser")
    public String addUser(User t){
        ObjectMapper mapper = new ObjectMapper();
        if(t==null) return "入参为空!";
        String result = "success";
        int i = userService.addUser(t);
        if(i<=0){
            result = "fail";
        }
        return result;

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        User user = null;
        try {
//            Thread.sleep(2000L);
//            Thread.sleep(1000L);
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("出现异常了：{}",e.getMessage(),e);
        }
        return user;

    }



}
