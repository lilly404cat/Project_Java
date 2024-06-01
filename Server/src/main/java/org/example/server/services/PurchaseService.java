package org.example.server.services;

import org.example.server.entity.Purchase;
import org.example.server.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

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

    public Purchase updatePurchase(Integer id, Purchase purchaseInfo) {
        try {
            Purchase purchase = purchaseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Purchase not found by id " + id));

            if (purchaseInfo.getQuantity() != null) {
                purchase.setQuantity(purchaseInfo.getQuantity());
            }
            if (purchaseInfo.getPrice() != null) {
                purchase.setPrice(purchaseInfo.getPrice());
            }
            if (purchaseInfo.getPurchaseDate() != null) {
                purchase.setPurchaseDate(purchaseInfo.getPurchaseDate());
            }
            if (purchaseInfo.getMedicine() != null) {
                purchase.setMedicine(purchaseInfo.getMedicine());
            }
            if (purchaseInfo.getSupplier() != null) {
                purchase.setSupplier(purchaseInfo.getSupplier());
            }

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
}
