package com.example.usage_service.repository;

import com.example.usage_service.model.UsageHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsageHourRepository extends JpaRepository<UsageHour, LocalDateTime> {

    Optional<UsageHour> findByHour(LocalDateTime hour);

    List<UsageHour> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
