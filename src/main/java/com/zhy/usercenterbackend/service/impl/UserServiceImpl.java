package com.zhy.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.usercenterbackend.mapper.UserMapper;
import com.zhy.usercenterbackend.model.dto.User;
import com.zhy.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zenghaoyuan
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-05-25 16:39:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    public static final String SALT = "user";
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        // 账户不能包含特殊字符
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+[a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(userAccount);
        if (!matcher.matches()) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 密码和校验密码是否相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+checkPassword).getBytes());

        // 存储用户注册数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }
}




