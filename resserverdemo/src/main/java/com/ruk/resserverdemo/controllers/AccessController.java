package com.ruk.resserverdemo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/resserver")
public class AccessController {

    @GetMapping
    public String res() {
        return "Your data is gone...";
    }

}
