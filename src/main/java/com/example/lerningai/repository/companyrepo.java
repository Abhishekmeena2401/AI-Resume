package com.example.lerningai.repository;

import com.example.lerningai.entity.companydata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface companyrepo extends JpaRepository<companydata, Long> {
    // JpaRepository gives us: save(), findAll(), findById(), deleteById() for free!
}
