package com.P1L15.airport.data.persistance.repository;

import com.P1L15.airport.data.persistance.entity.FlightEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, String> {
  Collection<FlightEntity> findByLastSeenBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
