package com.example.lerningai.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "resume_data")
public class resumedata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String userName;

    private int atsScore;

    private String predictedJobRole;

    @Column(columnDefinition = "TEXT")
    private String topSkills;          // stored as JSON string

    @Column(columnDefinition = "TEXT")
    private String importantSuggestions; // top 3 resume tips stored as plain text

    @Column(columnDefinition = "TEXT")
    private String overallFeedback;

    @CreationTimestamp
    private LocalDateTime analyzedAt;

    // ---------- Getters ----------
    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getUserName() { return userName; }
    public int getAtsScore() { return atsScore; }
    public String getPredictedJobRole() { return predictedJobRole; }
    public String getTopSkills() { return topSkills; }
    public String getImportantSuggestions() { return importantSuggestions; }
    public String getOverallFeedback() { return overallFeedback; }
    public LocalDateTime getAnalyzedAt() { return analyzedAt; }

    // ---------- Setters ----------
    public void setId(Long id) { this.id = id; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setAtsScore(int atsScore) { this.atsScore = atsScore; }
    public void setPredictedJobRole(String predictedJobRole) { this.predictedJobRole = predictedJobRole; }
    public void setTopSkills(String topSkills) { this.topSkills = topSkills; }
    public void setImportantSuggestions(String importantSuggestions) { this.importantSuggestions = importantSuggestions; }
    public void setOverallFeedback(String overallFeedback) { this.overallFeedback = overallFeedback; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
}
