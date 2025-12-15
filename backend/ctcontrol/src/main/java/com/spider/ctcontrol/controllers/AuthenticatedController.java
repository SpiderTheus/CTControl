package com.spider.ctcontrol.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.spider.ctcontrol.services.securityServices.AuthenticatedService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class AuthenticatedController {
    private final AuthenticatedService authenticatedService;

    public AuthenticatedController(AuthenticatedService authenticatedService) {
        this.authenticatedService = authenticatedService;
    }

    @PostMapping("authenticate")
    public String authenticated(Authentication authentication) {  
        return authenticatedService.generateToken(authentication);
    }
    

}
