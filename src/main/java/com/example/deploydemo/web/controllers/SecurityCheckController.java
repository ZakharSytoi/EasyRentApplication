package com.example.deploydemo.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/easyrent-api/v1/security_checks")
public class SecurityCheckController {
    @GetMapping("unauthorized_get")
    public ResponseEntity<String> unauthorizedGet() {
        return new ResponseEntity<>("Success from unauthorized get!", HttpStatus.OK);
    }

    @PostMapping("unauthorized_post")
    public ResponseEntity<String> unauthorizedPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from unauthorized post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("authorized_as_owner_get")
    public ResponseEntity<String> authorizedAsOwnerGet() {
        return new ResponseEntity<>("Success from authorized as owner get!", HttpStatus.OK);
    }

    @PostMapping("authorized_as_owner_post")
    public ResponseEntity<String> authorizedAsOwnerPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from authorized as owner post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("authorized_as_tenant_get")
    public ResponseEntity<String> authorizedAsTenantGet() {
        return new ResponseEntity<>("Success from authorized as tenant get!", HttpStatus.OK);
    }

    @PostMapping("authorized_as_tenant_post")
    public ResponseEntity<String> authorizedAsTenantPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from authorized as tenant post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("role_check")
    public ResponseEntity<String> checkRoleGet(Principal principal) {
        if (principal != null)
            return new ResponseEntity<>("You are logged in as: " + principal.getName(), HttpStatus.OK);
        else return new ResponseEntity<>("You are logged in as: guest", HttpStatus.OK);

    }
}
