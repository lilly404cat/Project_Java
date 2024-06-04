package org.example.server.repository;

import org.example.server.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * interface for the ConsumptionRepository
 */
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {
    List<Consumption> findByDepartmentId(Integer departmentId);
    List<Consumption> findByDepartmentIdAndConsumptionDateBetween(Integer departmentId, LocalDate startDate, LocalDate endDate);
    @Query("SELECT c.id FROM Consumption c WHERE c.medicine.name = :medicine AND c.department.name = :department")
    Integer findIdsByMedicineNameAndDepartmentName(@Param("medicine") String medicine, @Param("department") String department);
}
