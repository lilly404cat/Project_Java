package org.example.server.repository;

import org.example.server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByDepartmentId(Integer departmentId);
    @Query("SELECT s.id FROM Stock s WHERE s.medicine.name = :medicine AND s.department.name = :department")
    Integer findIdsByMedicineNameAndDepartmentName(@Param("medicine") String medicine, @Param("department") String department);
}
