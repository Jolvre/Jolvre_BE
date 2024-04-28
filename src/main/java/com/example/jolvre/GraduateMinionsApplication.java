package com.example.jolvre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GraduateMinionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraduateMinionsApplication.class, args);
    }

}
