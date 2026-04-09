package com.example.lerningai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Userdata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    // ---------- Getters ----------
    public Long getId()       { return id; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public String getPassword() { return password; }

    // ---------- Setters ----------
    public void setId(Long id)           { this.id = id; }
    public void setName(String name)     { this.name = name; }
    public void setEmail(String email)   { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
