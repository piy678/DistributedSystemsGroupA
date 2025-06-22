package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    List<UsageData> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
