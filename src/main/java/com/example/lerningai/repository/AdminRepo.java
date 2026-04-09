package com.example.lerningai.repository;

import com.example.lerningai.entity.Admindata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admindata, Long> {

    // Find admin by email (used during login)
    Optional<Admindata> findByEmail(String email);
}
