package org.example.gui;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageEntry {
    public Long id;
    public String hour;
    public double communityProduced;
    public double communityUsed;
    public double gridUsed;
}
