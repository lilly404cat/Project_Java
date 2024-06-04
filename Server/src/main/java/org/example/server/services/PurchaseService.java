package org.example.server.services;

import org.example.server.entity.Purchase;
import org.example.server.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * the PurchaseService class
 */
@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private SupplierService supplierService;

    /**
     * returns a list with all the purchases
     * @return List
     */
    public List<Purchase> getAllPurchases() {
        try {
            return purchaseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all purchases", e);
        }
    }

    /**
     * returns a purchase by an id
     * @param id the id
     * @return Purchase
     */
    public Purchase getPurchaseById(Integer id) {
        try {
            Optional<Purchase> purchase = purchaseRepository.findById(id);
            return purchase.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting purchase by id", e);
        }
    }

    /**
     * creates a new purchase
     * @param purchase the purchase
     * @return Purchase
     */
    public Purchase createPurchase(Purchase purchase) {
        try {
            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            throw new RuntimeException("Error creating purchase", e);
        }
    }

    /**
     * updates a purchase
     * @param id the id
     * @param quantity the quantity
     * @param price the price
     * @return Purchase
     */
    public Purchase updatePurchase(Integer id, Integer quantity, Double price) {
        try {
            Purchase purchase = purchaseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Purchase not found by id " + id));

            purchase.setQuantity(quantity);
            purchase.setPrice(price);
            purchase.setPurchaseDate(Date.valueOf(LocalDate.now()));

            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            throw new RuntimeException("Error updating purchase", e);
        }
    }

    /**
     * deletes a purchase
     * @param id the id
     */
    public void deletePurchase(Integer id) {
        try {
            purchaseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting purchase", e);
        }
    }

    /**
     * finds a purchase by an id and a  supplier
     * @param medicineName the name of the medicine
     * @param supplierName the name of the supplier
     * @return Integer
     */
    public Integer findPurchaseIdByMedicineAndSupplier(String medicineName, String supplierName) {
        return purchaseRepository.findByMedicineNameAndSupplierName(medicineName, supplierName);
    }
}
