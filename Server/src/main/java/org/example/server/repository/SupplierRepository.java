package org.example.server.repository;

import org.example.server.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface for the SupplierRepository
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByName(String name);
}
