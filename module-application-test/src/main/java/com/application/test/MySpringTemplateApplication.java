package com.application.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.application.test", "com.projectname.core"})
public class MySpringTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringTemplateApplication.class, args);
    }
}
