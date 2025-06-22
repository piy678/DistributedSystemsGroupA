package org.example.dsproject.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter

public class CurrentPercentage {


    @Id
    @Column(name = "\"hour\"")
    private LocalDateTime hour;

    private Double communityDepleted;
    private Double gridPortion;

    public CurrentPercentage(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }


    public CurrentPercentage() {

    }

}
