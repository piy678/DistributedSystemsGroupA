package org.example.gui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentStatus {
    public LocalDateTime hour;
    public double communityDepleted;
    public double gridPortion;
}
