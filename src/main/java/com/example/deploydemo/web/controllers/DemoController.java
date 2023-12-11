package com.example.deploydemo.web.controllers;

import com.example.deploydemo.repository.model.User;
import com.example.deploydemo.repository.daos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ResponseApartmentObject> getApartmentObject() {
        return new ResponseEntity<>(new ResponseApartmentObject("Home", 124L, "Mazoviecka 10", "Moje dobre mieszkanie"), HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
