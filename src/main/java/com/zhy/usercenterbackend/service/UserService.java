package com.zhy.usercenterbackend.service;

import com.zhy.usercenterbackend.model.dto.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zenghaoyuan
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-05-25 16:39:54
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

}
