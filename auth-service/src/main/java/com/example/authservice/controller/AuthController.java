
package com.example.authservice.controller;

import com.example.authservice.dto.LoginRequest;
import com.example.authservice.service.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        if ("admin".equals(username)
                && "1234".equals(password)) {

            return ResponseEntity.ok(
                    jwtService.generateToken(username, "ADMIN")
            );
        }

        if ("user".equals(username)
                && "1234".equals(password)) {

            return ResponseEntity.ok(
                    jwtService.generateToken(username, "USER")
            );
        }

        return ResponseEntity.status(401)
                .body("Invalid username or password");
    }
}
