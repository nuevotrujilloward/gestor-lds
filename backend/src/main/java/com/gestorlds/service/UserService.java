package com.gestorlds.service;

import com.gestorlds.dto.CreateUserRequest;
import com.gestorlds.dto.LoginRequest;
import com.gestorlds.dto.LoginResponse;
import com.gestorlds.dto.UserDto;
import com.gestorlds.entity.Role;
import com.gestorlds.entity.User;
import com.gestorlds.repository.RoleRepository;
import com.gestorlds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .nombre(user.getNombre())
                .rolNombre(user.getRol().getNombre())
                .build();
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya existe");
        }

        Role role = roleRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = User.builder()
                .email(request.getEmail())
                .nombre(request.getNombre())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rol(role)
                .activo(true)
                .build();

        User savedUser = userRepository.save(user);

        return mapToUserDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToUserDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getActiveUsers() {
        return userRepository.findByActivo(true).stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nombre(user.getNombre())
                .rolNombre(user.getRol().getNombre())
                .activo(user.getActivo())
                .build();
    }
}