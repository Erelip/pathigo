package com.leplei.pathigo.controllers;

import com.leplei.pathigo.dto.LoginRequest;
import com.leplei.pathigo.dto.LoginResponse;
import com.leplei.pathigo.dto.RegisterRequest;
import com.leplei.pathigo.models.RegisterCommand;
import com.leplei.pathigo.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest dto) {
        String token = authenticationService.login(dto.email(), dto.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest dto) {
        String token = authenticationService.register(new RegisterCommand(
                dto.email(), dto.username(), dto.password(), dto.nickname(), dto.phoneNumber()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }
}