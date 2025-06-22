package com.example.percentage_service.repository;

import com.example.percentage_service.model.UsageHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsageHourRepository extends JpaRepository<UsageHour, LocalDateTime> {

    Optional<UsageHour> findByHour(LocalDateTime hour);

    List<UsageHour> findAllByHourBetween(LocalDateTime start, LocalDateTime end);

}
