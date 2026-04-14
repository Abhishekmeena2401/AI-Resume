package com.example.lerningai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Coursedata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;   // URL to the course



    // ---------- Getters ----------
    public Long getId()             { return id; }
    public String getName()         { return name; }
    public String getDescription()  { return description; }
    public String getLink()         { return link; }

    // ---------- Setters ----------
    public void setId(Long id)                     { this.id = id; }
    public void setName(String name)               { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLink(String link)               { this.link = link; }
}
