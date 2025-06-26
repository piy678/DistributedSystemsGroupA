/*package org.example.dsproject.restapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UsageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"hour\"", unique = true)
    private LocalDateTime hour;

    private Double communityProduced;
    private Double communityUsed;
    private Double gridUsed;
    

    public UsageData() {

    }
    public UsageData(LocalDateTime hour, double produced, double used, double grid) {
        this.hour = hour;
        this.communityProduced = produced;
        this.communityUsed = used;
        this.gridUsed = grid;
    }


}
*/