package com.vupt172.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fake")
public class FakeController {
    @GetMapping
    public String hello(){
        return "/hello";
    }
}
