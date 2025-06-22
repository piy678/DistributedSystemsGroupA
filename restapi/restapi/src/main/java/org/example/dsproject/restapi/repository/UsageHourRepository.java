package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.UsageHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageHourRepository extends JpaRepository<UsageHour, LocalDateTime> {
    List<UsageHour> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
