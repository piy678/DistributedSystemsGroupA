package com.example.percentage_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "usage_hour")
public class UsageHour {
    // Getter & Setter
    @Id
    @Column(name = "\"hour\"")
    private LocalDateTime hour;

    private double communityProduced;
    private double communityUsed;
    private double gridUsed;


    public UsageHour() {}


    public UsageHour(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed) {
        this.hour = hour;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

}
