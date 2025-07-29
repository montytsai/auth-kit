package io.github.montytsai.authkit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String helloWorld() {
        return "Project Auth-Kit is running!";
    }

}