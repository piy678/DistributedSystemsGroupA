package org.example.energyproducer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EnergyMessage {

    private String type = "PRODUCER";
    private String association = "COMMUNITY";
    private double kwh;
    private Instant datetime;

    @Override
    public String toString() {
        return "EnergyMessage{" +
                "type='" + type + '\'' +
                ", association='" + association + '\'' +
                ", kwh=" + kwh +
                ", datetime=" + datetime +
                '}';
    }
}
