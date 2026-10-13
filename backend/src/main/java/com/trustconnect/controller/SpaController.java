package com.trustconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({
        "/register",
        "/dashboard",
        "/donors",
        "/donations",
        "/expenses",
        "/members",
        "/reports"
    })
    public String forwardToFrontend() {
        return "forward:/index.html";
    }
}