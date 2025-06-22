package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, LocalDateTime> {
CurrentPercentage findTopByOrderByHourDesc();
CurrentPercentage findByHour(LocalDateTime hour);

    List<CurrentPercentage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
