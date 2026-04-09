package com.example.lerningai.controler;

import com.example.lerningai.entity.Userdata;
import com.example.lerningai.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")   // Allows frontend (HTML file) to call this API
public class UserController {

    @Autowired
    private UserRepo userRepo;

    // ─── REGISTER ─────────────────────────────────────────────────
    // POST http://localhost:8080/register
    // Body (JSON): { "name": "...", "email": "...", "password": "..." }
    @PostMapping("/register")
    public String register(@RequestBody Userdata user) {

        // Check if email already exists
        Optional<Userdata> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return "{\"success\": false, \"message\": \"Email already registered.\"}";
        }

        // Save new user to DB
        userRepo.save(user);
        return "{\"success\": true, \"message\": \"Account created successfully!\"}";
    }

    // ─── LOGIN ────────────────────────────────────────────────────
    // POST http://localhost:8080/login
    // Body (JSON): { "email": "...", "password": "..." }
    @PostMapping("/login")
    public String login(@RequestBody Userdata loginRequest) {

        Optional<Userdata> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return "{\"success\": false, \"message\": \"Email not found.\"}";
        }

        Userdata user = userOpt.get();

        // Simple password check (plain text - beginner friendly)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return "{\"success\": false, \"message\": \"Wrong password.\"}";
        }

        // Return name + email so frontend can save to localStorage
        return "{\"success\": true, \"name\": \"" + user.getName() + "\", \"email\": \"" + user.getEmail() + "\"}";
    }
}
