package org.example.server.services;

import org.example.server.entity.Medicine;
import org.example.server.entity.Purchase;
import org.example.server.entity.Supplier;
import org.example.server.repository.MedicineRepository;
import org.example.server.repository.PurchaseRepository;
import org.example.server.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private SupplierService supplierService;

    public List<Purchase> getAllPurchases() {
        try {
            return purchaseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all purchases", e);
        }
    }

    public Purchase getPurchaseById(Integer id) {
        try {
            Optional<Purchase> purchase = purchaseRepository.findById(id);
            return purchase.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting purchase by id", e);
        }
    }

    public Purchase createPurchase(Purchase purchase) {
        try {
            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            throw new RuntimeException("Error creating purchase", e);
        }
    }
    public Purchase updatePurchase(Integer id, Integer quantity, Double price) {
        try {
            // Find the purchase by id
            Purchase purchase = purchaseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Purchase not found by id " + id));

            // Update quantity and price
            purchase.setQuantity(quantity);
            purchase.setPrice(price);

            // Save and return the updated purchase
            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            throw new RuntimeException("Error updating purchase", e);
        }
    }


    public void deletePurchase(Integer id) {
        try {
            purchaseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting purchase", e);
        }
    }

    public Integer findPurchaseIdByMedicineAndSupplier(String medicineName, String supplierName) {
        return purchaseRepository.findByMedicineNameAndSupplierName(medicineName, supplierName);
    }
}
