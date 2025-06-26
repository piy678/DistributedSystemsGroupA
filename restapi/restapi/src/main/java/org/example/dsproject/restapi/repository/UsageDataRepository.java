/*package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.UsageHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsageHourRepository extends JpaRepository<UsageHour, LocalDateTime> {
    List<UsageHour> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}*/