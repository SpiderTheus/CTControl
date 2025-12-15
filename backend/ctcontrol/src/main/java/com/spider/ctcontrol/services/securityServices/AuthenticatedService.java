package com.spider.ctcontrol.services.securityServices;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedService {
    private final JwtService jwtService;

    public AuthenticatedService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String generateToken(Authentication authentication) {

        return jwtService.generateToken(authentication);
    }
}
