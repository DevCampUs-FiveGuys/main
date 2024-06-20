package com.example.devcampus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.springsecurity6.util.SpringSecurityContextUtils;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("data.mapper")
@ComponentScan({"com.example.devcampus","data.*","controller.*","data.naver.cloud","config"})
public class DevCampUsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevCampUsApplication.class, args);
    }

}
