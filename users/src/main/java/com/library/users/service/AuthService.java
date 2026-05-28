package com.library.users.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.users.dto.AuthResponse;
import com.library.users.dto.LoginRequest;
import com.library.users.dto.RegisterRequest;
import com.library.users.entity.User;
import com.library.users.exception.AuthenticationException;
import com.library.users.exception.ValidationException;
import com.library.users.repository.UserRepository;
import com.library.users.util.JwtUtil;

@Service

public class AuthService {


  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;


  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;

  }

  public AuthResponse register(RegisterRequest request) {
    // Validar que el username no exista
    if (userRepository.findByUsername(request.username()).isPresent()) {

      throw new ValidationException("El username ya existe");

    }



    // Validar que username y password no estén vacíos

    if (request.username() == null || request.username().isBlank()) {
      throw new ValidationException("El username no puede estar vacío");
    }
    if (request.password() == null || request.password().isBlank()) {
      throw new ValidationException("La contraseña no puede estar vacía");
    }



    // Hash la contraseña
    String passwordHash = passwordEncoder.encode(request.password());
    // Crear y guardar el usuario
    User user = User.builder()
        .username(request.username())
        .passwordHash(passwordHash)
        .build();
    userRepository.save(user);



    // Generar JWT
    String token = jwtUtil.generateToken(user.getUsername());
    return new AuthResponse(token, jwtUtil.getExpiration(), user.getUsername());
  }

  public AuthResponse login(LoginRequest request) {
    // Validar que username y password no estén vacíos

    if (request.username() == null || request.username().isBlank()) {
      throw new ValidationException("El username no puede estar vacío");
    }
    if (request.password() == null || request.password().isBlank()) {
      throw new ValidationException("La contraseña no puede estar vacía");
    }
    // Buscar usuario por username
    User user = userRepository.findByUsername(request.username())
        .orElseThrow(() -> new AuthenticationException("El username o contraseña son inválidos"));

    // Validar contraseña
    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new AuthenticationException("El username o contraseña son inválidos");
    }

    // Generar JWT
    String token = jwtUtil.generateToken(user.getUsername());
    return new AuthResponse(token, jwtUtil.getExpiration(), user.getUsername());

  }
  public boolean validateToken(String token) {
    return jwtUtil.validateToken(token);
  }

}