package com.asupranovich.revision.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AspectTestController {

    @CollectRequestInfo
    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public ResponseEntity<String> greet(HttpServletRequest request) {
        return ResponseEntity.ok("Hello, User");
    }

    @CollectRequestInfo
    @PostMapping("/greet")
    public String postGreet(HttpServletRequest request) {
        return "Hello, User";
    }

    @CollectRequestInfo
    @PutMapping("/greet")
    public String putGreet(HttpServletRequest request) {
        if (true) {
            throw new RuntimeException("Error Test");
        }
        return "Hello, User";
    }

}
