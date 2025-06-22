package com.example.percentage_service.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class CurrentPercentage {

    @Id
    @Column(name = "\"hour\"")
    private LocalDateTime hour;

    private double communityDepleted;
    private double gridPortion;

    public CurrentPercentage() {}


    public CurrentPercentage(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }


}
