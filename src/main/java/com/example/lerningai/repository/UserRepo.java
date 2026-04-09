package com.example.lerningai.repository;

import com.example.lerningai.entity.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Userdata, Long> {

    // Spring Data JPA automatically creates this query for us!
    // It finds a user by their email address.
    Optional<Userdata> findByEmail(String email);
}
