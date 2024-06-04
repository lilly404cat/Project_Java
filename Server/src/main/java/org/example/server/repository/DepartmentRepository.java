package org.example.server.repository;

import org.example.server.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface for the DepartmentRepository
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByName(String name);
}
