package com.example.demo.repository;

import com.example.demo.repository.dao.Transportation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    @EntityGraph(attributePaths = {"origin", "destination"})
    List<Transportation> findAll();

}
