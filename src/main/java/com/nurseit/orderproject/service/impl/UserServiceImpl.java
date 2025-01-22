package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.dto.LoginRequestDto;
import com.nurseit.orderproject.dto.LoginResponseDto;
import com.nurseit.orderproject.dto.RegistrationRequestDto;
import com.nurseit.orderproject.entity.Role;
import com.nurseit.orderproject.entity.User;
import com.nurseit.orderproject.repository.UserRepository;
import com.nurseit.orderproject.service.RoleService;
import com.nurseit.orderproject.service.UserService;
import com.nurseit.orderproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleService roleService;

    @Override
    public void registration(RegistrationRequestDto registrationRequest) {
        if (repository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with the same username already exists");
        }
        create(registrationRequest.getUsername(), registrationRequest.getPassword());
    }


    private void create(String username, String password) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.findByName("ROLE_USER"));
        User user = User.builder()
                .roles(role)
                .isEnabled(true)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        repository.save(
                user
        );
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<User> userOptional = repository.findByUsername(loginRequestDto.getUsername());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return new LoginResponseDto(jwtUtil.generateToken(user.getUsername(), user.getRoles()));
    }
}
