package org.example.dsproject.restapi.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrentPercentageDto {
    // Getter & Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime hour;

    private double communityDepleted;
    private double gridPortion;

    public CurrentPercentageDto() {}

    public CurrentPercentageDto(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    public CurrentPercentageDto(double v, double v1) {
    }
}
