package com.example.recipeapp;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final Map<String, Bucket> rateLimitBuckets = new ConcurrentHashMap<>();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private Bucket getRateLimitBucket(String username) {
        return rateLimitBuckets.computeIfAbsent(username, k -> {
            Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
            return Bucket.builder().addLimit(limit).build();
        });
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        logger.info("Registration attempt for username: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            logger.warn("Registration failed: Username {} already exists", user.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getUsername());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Bucket bucket = getRateLimitBucket(user.getUsername());
        if (!bucket.tryConsume(1)) {
            logger.warn("Rate limit exceeded for login attempt by username: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many login attempts. Try again later.");
        }

        logger.info("Login attempt for username: {}", user.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            String token = jwtUtil.generateToken(user.getUsername());
            logger.info("User logged in successfully: {}", user.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.error("Login failed for username: {}. Error: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}