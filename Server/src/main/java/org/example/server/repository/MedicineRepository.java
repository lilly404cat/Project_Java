package org.example.server.repository;

import org.example.server.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface for the MedicineRepository
 */
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    Medicine findTopByOrderByIdDesc();
    Medicine findByName(String name);
}

