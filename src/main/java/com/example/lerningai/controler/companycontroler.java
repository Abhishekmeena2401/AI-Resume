package com.example.lerningai.controler;

import com.example.lerningai.entity.companydata;
import com.example.lerningai.repository.companyrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class companycontroler {

    @Autowired
    private companyrepo repo;

    // ─── GET ALL COMPANIES ────────────────────────────────────────
    // GET http://localhost:8080/companies
    @GetMapping("/companies")
    public List<companydata> getAllCompanies() {
        return repo.findAll();   // Returns all companies from DB as JSON
    }

    // ─── ADD COMPANY (Admin only via admin panel) ─────────────────
    // POST http://localhost:8080/companies
    // Body (JSON): { "name": "...", "description": "...", "packageOffered": "...", "applyLink": "..." }
    @PostMapping("/companies")
    public companydata addCompany(@RequestBody companydata company) {
        return repo.save(company);   // Saves to DB and returns saved object
    }

    // ─── DELETE COMPANY ───────────────────────────────────────────
    // DELETE http://localhost:8080/companies/1
    @DeleteMapping("/companies/{id}")
    public String deleteCompany(@PathVariable Long id) {
        repo.deleteById(id);
        return "{\"success\": true, \"message\": \"Company deleted.\"}";
    }
}
