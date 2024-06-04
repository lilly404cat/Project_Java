package org.example.server.repository;

import org.example.server.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("SELECT p.id FROM Purchase p WHERE p.medicine.name = :medicine AND p.supplier.name = :supplier")
    Integer findByMedicineNameAndSupplierName(@Param("medicine") String medicine, @Param("supplier") String supplier);
}
