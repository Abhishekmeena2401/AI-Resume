//package com.example.lerningai.repository;
//
//import com.example.lerningai.entity.resumedata;
//import org.apache.catalina.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository // Optional but recommended for exception translation
//public interface Resumerepo extends JpaRepository<resumedata, Long> {
//    // Custom query methods can be added here
//}








package com.example.lerningai.repository;

import com.example.lerningai.entity.resumedata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Resumerepo extends JpaRepository<resumedata, Long> {
}