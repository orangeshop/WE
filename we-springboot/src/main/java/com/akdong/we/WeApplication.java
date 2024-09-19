package com.akdong.we;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class WeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeApplication.class, args);
    }
}
