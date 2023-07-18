package com.articles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.articles")
public class ArticlesApp {

    public static void main(String[] args) {
        SpringApplication.run(ArticlesApp.class, args);
    }

}
