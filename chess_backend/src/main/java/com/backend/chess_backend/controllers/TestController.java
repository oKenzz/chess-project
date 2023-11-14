package com.backend.chess_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class TestController {
    
    @GetMapping("/add")
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        return a + b;
    }


}
