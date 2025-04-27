package org.example.dsproject.restapi.model;

import java.time.LocalDateTime;

public class CurrentPercentage {

    private Long id;
    private Double communityDepleted;
    private Double gridPortion;
    private LocalDateTime hour;

    // Konstruktoren
    public CurrentPercentage() {
    }

    public CurrentPercentage(Long id, Double communityDepleted, Double gridPortion, LocalDateTime hour) {
        this.id = id;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
        this.hour = hour;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCommunityDepleted() {
        return communityDepleted;
    }

    public void setCommunityDepleted(Double communityDepleted) {
        this.communityDepleted = communityDepleted;
    }

    public Double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(Double gridPortion) {
        this.gridPortion = gridPortion;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}
