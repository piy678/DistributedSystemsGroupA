package org.example.dsproject.restapi.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "usage_hour")
public class UsageHour {

    @Id
    private LocalDateTime hour;

    private Double communityProduced;
    private Double communityUsed;
    private Double gridUsed;

    // Getter + Setter
    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public Double getCommunityProduced() { return communityProduced; }
    public void setCommunityProduced(Double communityProduced) { this.communityProduced = communityProduced; }

    public Double getCommunityUsed() { return communityUsed; }
    public void setCommunityUsed(Double communityUsed) { this.communityUsed = communityUsed; }

    public Double getGridUsed() { return gridUsed; }
    public void setGridUsed(Double gridUsed) { this.gridUsed = gridUsed; }
}
