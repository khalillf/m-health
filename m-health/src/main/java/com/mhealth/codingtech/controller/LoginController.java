package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.dto.LoginRequest;
import com.mhealth.codingtech.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> result = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (result == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Create a response map to return as JSON
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("userType", result.get("userType"));
        response.put("userId", result.get("userId"));

        return ResponseEntity.ok(response);
    }
}
