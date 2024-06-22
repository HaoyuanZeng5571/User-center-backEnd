package com.zhy.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.usercenterbackend.model.dto.User;
import com.zhy.usercenterbackend.service.UserService;
import com.zhy.usercenterbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zenghaoyuan
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-05-25 16:39:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




