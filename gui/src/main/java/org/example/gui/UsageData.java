package org.example.gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsageData {
    private Long id;
    private Double communityProduced;
    private Double communityUsed;
    private Double gridUsed;
    private java.time.LocalDateTime hour;
    private String timestamp;
    private double value;



        public UsageData(String timestamp, double value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        public String getTimestamp() { return timestamp; }
        public double getValue() { return value; }
    public Double getCommunityProduced() { return communityProduced; }
    public Double getCommunityUsed() { return communityUsed; }
    public Double getGridUsed() { return gridUsed; }
    public java.time.LocalDateTime getHour() { return hour; }

}


