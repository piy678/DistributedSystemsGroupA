package com.example.percentage_service.repository;

import com.example.percentage_service.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, LocalDateTime> {

    Optional<CurrentPercentage> findTopByOrderByHourDesc(); // liefert den neuesten Eintrag

    Optional<CurrentPercentage> findByHour(LocalDateTime hour);

    List<CurrentPercentage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
