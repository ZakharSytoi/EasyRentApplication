package com.example.deploydemo.service.util;

import com.example.deploydemo.repository.daos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;
    public Long getUserIdFromContext(){
        Long userId;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.equals("anonymousUser")) {
            userId = userRepository.findByEmail(userName).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            ).getId();
        } else userId = -1L;
        return userId;
    }
}
