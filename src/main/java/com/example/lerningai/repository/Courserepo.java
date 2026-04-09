package com.example.lerningai.repository;

import com.example.lerningai.entity.Coursedata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Courserepo extends JpaRepository<Coursedata, Long> {
    // JpaRepository gives us: save(), findAll(), findById(), deleteById() for free!
}
