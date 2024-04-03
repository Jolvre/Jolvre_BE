package com.example.graduate_minions.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/aa")
    public String dd() {
        return "JJJ";
    }

    @GetMapping("/v1/api/aa")
    public String ddd() {
        return " ";
    }

}
