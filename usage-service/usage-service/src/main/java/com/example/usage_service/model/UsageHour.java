package com.example.usage_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsageHour {

    @Id
    private LocalDateTime hour;

    private Double communityProduced = 0.0;
    private Double communityUsed = 0.0;
    private Double gridUsed = 0.0;

    private Double totalProduced = 0.0;
    private Double totalUsed = 0.0;

    public UsageHour(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed) {
        this.hour = hour;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
        this.totalProduced = 0.0;
        this.totalUsed = 0.0;
    }
}
