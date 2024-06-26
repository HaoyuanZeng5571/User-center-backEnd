package com.zhy.usercenterbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhy.usercenterbackend.mapper")
public class UserCenterBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterBackEndApplication.class, args);
    }

}
