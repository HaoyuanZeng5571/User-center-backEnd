package com.zhy.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.usercenterbackend.mapper.UserMapper;
import com.zhy.usercenterbackend.model.dto.User;
import com.zhy.usercenterbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zenghaoyuan
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-05-25 16:39:54
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private UserMapper userMapper;

    // 盐值
    private static final String SALT = "user";

    // 用户登录态key
    private static final String USER_LOGIN_STATE = "user_login_state";
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            // todo 修改为自定义异常
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

    /**
     * 用户登陆
     * @param userAccount
     * @param userPassword
     * @return 返回脱敏后的用户信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        // 账户不能包含特殊字符
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+[a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(userAccount);
        if (!matcher.matches()) {
            return null;
        }

        // 2.对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        // 查询用户是否存在
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login error");
            return null;
        }

        // 3.数据脱敏
        User cleanUser = new User();
        cleanUser.setId(user.getId());
        cleanUser.setUserName(user.getUserName());
        cleanUser.setUserAccount(user.getUserAccount());
        cleanUser.setAvatarUrl(user.getAvatarUrl());
        cleanUser.setGender(user.getGender());
        cleanUser.setPhone(user.getPhone());
        cleanUser.setEmail(user.getEmail());
        cleanUser.setUserStatus(user.getUserStatus());
        cleanUser.setCreateTime(user.getCreateTime());

        // 4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, cleanUser);

        return cleanUser;
    }
}




