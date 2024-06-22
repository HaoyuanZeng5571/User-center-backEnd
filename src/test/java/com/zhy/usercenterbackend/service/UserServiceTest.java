package com.zhy.usercenterbackend.service;

import com.zhy.usercenterbackend.model.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户服务测试
 *
 * @author zhy
 */

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {

        User user = new User();
        user.setId(0L);
        user.setUserName("");
        user.setUserAccount("");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("");
        user.setPhone("");
        user.setEmail("");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        userService.save(user);
    }

}