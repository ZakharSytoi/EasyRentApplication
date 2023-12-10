package com.example.deploydemo.service;

import com.example.deploydemo.repository.daos.UserRepository;
import com.example.deploydemo.repository.model.User;
import com.example.deploydemo.service.dto.userRegistrationDtoRequest;
import com.example.deploydemo.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleService roleservice;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(userRegistrationDtoRequest request) throws UserAlreadyExistsException {
        if(findByEmail(request.email()).isPresent()){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", request.email()));
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
    }
}
