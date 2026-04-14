package com.example.lerningai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class companydata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String packageOffered;   // e.g. "6 LPA – 12 LPA"

    private String applyLink;        // URL to apply


    // ---------- Getters ----------
    public Long getId()                { return id; }
    public String getName()            { return name; }
    public String getDescription()     { return description; }
    public String getPackageOffered()  { return packageOffered; }
    public String getApplyLink()       { return applyLink; }

    // ---------- Setters ----------
    public void setId(Long id)                         { this.id = id; }
    public void setName(String name)                   { this.name = name; }
    public void setDescription(String description)     { this.description = description; }
    public void setPackageOffered(String packageOffered) { this.packageOffered = packageOffered; }
    public void setApplyLink(String applyLink)         { this.applyLink = applyLink; }
}
