package com.zhy.usercenterbackend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 2971191921711365378L;

    // 用户账户
    private String userAccount;

    // 用户密码
    private String userPassword;

}
