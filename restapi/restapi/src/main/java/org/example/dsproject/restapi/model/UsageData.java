package org.example.dsproject.restapi.model;

import java.time.LocalDateTime;

public class UsageData {

    private Long id;
    private Double communityProduced;
    private Double communityUsed;
    private Double gridUsed;
    private LocalDateTime hour;

    // Konstruktoren
    public UsageData() {
    }

    public UsageData(Long id, Double communityProduced, Double communityUsed, Double gridUsed, LocalDateTime hour) {
        this.id = id;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
        this.hour = hour;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(Double communityProduced) {
        this.communityProduced = communityProduced;
    }

    public Double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(Double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public Double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(Double gridUsed) {
        this.gridUsed = gridUsed;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}
