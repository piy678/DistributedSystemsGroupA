package org.example.energyuser;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyMessage {

    private String type = "USER";
    private String association = "COMMUNITY";
    private double kwh;

    @JsonProperty("datetime")
    private LocalDateTime datetime;

    public EnergyMessage() {}

    public EnergyMessage(String type, String association, double kwh, LocalDateTime datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
    }

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
