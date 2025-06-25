package com.example.usage_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Setter
@Getter
public class EnergyMessage {
    private String type;
    private String association;
    private double kwh;
    private LocalDateTime datetime;

    @Override
    public String toString() {
        return "EnergyMessage{" +
                "type='" + type + '\'' +
                ", association='" + association + '\'' +
                ", kwh=" + kwh +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
