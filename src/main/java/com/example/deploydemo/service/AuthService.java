package com.example.deploydemo.service;

import com.example.deploydemo.repository.daos.UserRepository;
import com.example.deploydemo.repository.model.User;
import com.example.deploydemo.service.dto.LoginUserDtoRequest;
import com.example.deploydemo.service.dto.UserRegistrationDtoRequest;
import com.example.deploydemo.service.exception.UserAlreadyExistsException;
import com.example.deploydemo.service.security.UserDetailServiceImpl;
import com.example.deploydemo.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public String authenticateUser(LoginUserDtoRequest loginUserDtoRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDtoRequest.getUsername(),
                        loginUserDtoRequest.getPassword()
                )
        );
        UserDetails userDetails = userDetailService.loadUserByUsername(
                loginUserDtoRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }
    public void registerUser(UserRegistrationDtoRequest request) throws UserAlreadyExistsException {
        if(userService.findByEmail(request.email()).isPresent()){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", request.email()));
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRoles(Set.of(roleService.findRoleByName(request.role().name())));
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }
}
