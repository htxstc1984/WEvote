package com.hbgc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hbgc.mapper")
public class VoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
    }

}
