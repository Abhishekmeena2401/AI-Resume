package com.example.lerningai.controler;

import com.example.lerningai.entity.Coursedata;
import com.example.lerningai.repository.Courserepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Coursecontroler {

    @Autowired
    private Courserepo repo;

    // ─── GET ALL COURSES ──────────────────────────────────────────
    // GET http://localhost:8080/courses
    @GetMapping("/courses")
    public List<Coursedata> getAllCourses() {
        return repo.findAll();   // Returns all courses as JSON
    }

    // ─── ADD COURSE (Admin only via admin panel) ──────────────────
    // POST http://localhost:8080/courses
    // Body (JSON): { "name": "...", "description": "...", "link": "..." }
    @PostMapping("/courses")
    public Coursedata addCourse(@RequestBody Coursedata course) {
        return repo.save(course);
    }

    // ─── DELETE COURSE ────────────────────────────────────────────
    // DELETE http://localhost:8080/courses/1
    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        repo.deleteById(id);
        return "{\"success\": true, \"message\": \"Course deleted.\"}";
    }
}
