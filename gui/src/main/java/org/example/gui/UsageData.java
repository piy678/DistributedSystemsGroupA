package org.example.gui;

public class UsageData {
    private Long id;
    private Double communityProduced;
    private Double communityUsed;
    private Double gridUsed;
    private java.time.LocalDateTime hour;

    // Getter (nur die brauchst du)
    public Double getCommunityProduced() { return communityProduced; }
    public Double getCommunityUsed() { return communityUsed; }
    public Double getGridUsed() { return gridUsed; }
    public java.time.LocalDateTime getHour() { return hour; }

}
