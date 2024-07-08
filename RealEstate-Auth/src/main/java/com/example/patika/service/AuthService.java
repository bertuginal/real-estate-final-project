package com.example.patika.service;

import com.example.patika.dto.AuthRequest;
import com.example.patika.dto.AuthResponse;
import com.example.patika.entity.User;
import com.example.patika.repository.AuthRepository;
import com.example.patika.util.JwtUtil;
import com.example.patika.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final AuthRepository authRepository;

    private final JwtUtil jwtUtil;

    public AuthResponse getToken(AuthRequest request) throws Exception {
        User user = authRepository.findByEmail(request.getEmail());

        if (user == null) {
            log.error("No user with the entered e-mail was found!" + request.getEmail());
            throw new Exception("User not found!");
        }

        if (!UserUtil.isValidPassword(user.getPassword(), request.getPassword())) {
            log.error("User's password not valid! " + request.getEmail());
            throw new Exception("User's password not valid!");
        }
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

}
