package com.zhy.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhy.usercenterbackend.model.dto.User;
import com.zhy.usercenterbackend.model.request.UserLoginRequest;
import com.zhy.usercenterbackend.model.request.UserRegisterRequest;
import com.zhy.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Long UserRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        return l;
    }

    /**
     * 用户登录接口
     * @param userLoginRequest
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/login")
    public User UserLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {

        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, httpServletRequest);
    }

    @GetMapping("/search")
    public List<User> SearchUser(String userName) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("userName", userName);
        }
        return userService.list(queryWrapper);
    }

    @PostMapping("/delete")
    public Boolean DeleteUser(@RequestBody Long id) {

        if (id < 0) {
            return false;
        }
        return userService.removeById(id);
    }
}
