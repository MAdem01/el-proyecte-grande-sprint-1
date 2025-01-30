package com.codecool.codekickfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class CodeKickFcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeKickFcApplication.class, args);
    }

}
