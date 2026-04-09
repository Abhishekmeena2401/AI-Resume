package com.example.lerningai.controler;

import com.example.lerningai.entity.Admindata;
import com.example.lerningai.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")   // All admin endpoints start with /admin
public class Admincontroler {

    @Autowired
    private AdminRepo adminRepo;

    // ─── ADMIN LOGIN ──────────────────────────────────────────────
    // POST http://localhost:8080/admin/login
    // Body (JSON): { "email": "admin@smartplace.com", "password": "admin123" }
    @PostMapping("/login")
    public String adminLogin(@RequestBody Admindata loginRequest) {

        Optional<Admindata> adminOpt = adminRepo.findByEmail(loginRequest.getEmail());

        if (adminOpt.isEmpty()) {
            return "{\"success\": false, \"message\": \"Admin email not found.\"}";
        }

        Admindata admin = adminOpt.get();

        if (!admin.getPassword().equals(loginRequest.getPassword())) {
            return "{\"success\": false, \"message\": \"Wrong password.\"}";
        }

        return "{\"success\": true, \"message\": \"Admin login successful.\"}";
    }
}

// ─── HOW ADMIN CRUD WORKS ─────────────────────────────────────────
// Admin uses the same /companies and /courses endpoints (POST, DELETE)
// that are already defined in companycontroler and Coursecontroler.
// No need to duplicate them here — the admin dashboard HTML calls those same APIs.
