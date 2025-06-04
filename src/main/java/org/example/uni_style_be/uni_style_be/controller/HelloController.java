package org.example.uni_style_be.uni_style_be.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        var test = SecurityContextHolder.getContext().getAuthentication();
        return "hello";
    }

}
