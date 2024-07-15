package com.example.patika.controller;

import com.example.patika.dto.AuthRequest;
import com.example.patika.dto.AuthResponse;
import com.example.patika.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class AuthController {

	private final AuthService authService;

	// Kullanıcı email ve password bilgisine göre token oluşturma ve login işlemi
	@PostMapping(value = "/auth")
	public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest request) throws Exception {
		return new ResponseEntity<>(authService.getToken(request), HttpStatus.OK);
	}

}
