package com.zrz.mqtt.controller;

import com.zrz.mqtt.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * 登录
     * @param loginName 登录名
     * @param password 密码
     * @return Result
     */
    @PostMapping("login")
    public Result login(@RequestParam("loginName") String loginName,
                           @RequestParam("password") String password){

        return Result.success();
    }

    /**
     * 注册
     * @param loginName 登录名
     * @param password 密码
     * @return Result
     */
    @PostMapping("register")
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("password") String password){

        return Result.success();
    }
}
