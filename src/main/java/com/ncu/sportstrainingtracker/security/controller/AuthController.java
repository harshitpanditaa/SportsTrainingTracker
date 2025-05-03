package com.ncu.sportstrainingtracker.security.controller;

import com.ncu.sportstrainingtracker.security.entity.Role;
import com.ncu.sportstrainingtracker.security.entity.User;
import com.ncu.sportstrainingtracker.security.repository.UserRepository;
import com.ncu.sportstrainingtracker.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager manager, JwtUtil jwtUtil, UserRepository repo, PasswordEncoder encoder) {
        this.manager = manager;
        this.jwtUtil = jwtUtil;
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        var user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(encoder.encode(body.get("password")));
        user.setRoles(Set.of(Role.valueOf(body.get("role").toUpperCase())));
        repo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"))
        );

        var user = repo.findByUsername(body.get("username")).orElseThrow();
        return jwtUtil.generateToken(user.getUsername(),
                user.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet()));
    }
}
