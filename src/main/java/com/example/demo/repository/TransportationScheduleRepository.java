package com.example.demo.repository;

import com.example.demo.repository.dao.TransportationSchedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportationScheduleRepository extends JpaRepository<TransportationSchedule, Long> {
    @EntityGraph(attributePaths = {"transportation"})
    List<TransportationSchedule> findAll();

    @EntityGraph(attributePaths = {"transportation"})
    Optional<TransportationSchedule> findById(Long id);
}
