package org.example.server.controller;

import org.example.server.entity.Medicine;
import org.example.server.entity.Purchase;
import org.example.server.entity.Supplier;
import org.example.server.services.MedicineService;
import org.example.server.services.PurchaseService;
import org.example.server.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/hospital_Purchases/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchases() {
        try {
            List<Purchase> purchases = purchaseService.getAllPurchases();
            return ResponseEntity.ok(purchases);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Integer id) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(id);
            if (purchase != null) {
                return ResponseEntity.ok(purchase);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(
            @RequestParam Integer quantity,
            @RequestParam Double price,
            @RequestParam String medicine,
            @RequestParam String supplier
    ) {
        try {
            Integer medId = medicineService.findByName(medicine);
            Medicine lastMedicine = medicineService.getMedicineById(medId);
            if (lastMedicine == null) {
                return ResponseEntity.status(404).body(null);
            }
            Integer supId=supplierService.findByName(supplier);
            Supplier lastSupplier = supplierService.findLastInsertedSupplier();
            if (lastSupplier == null) {
                return ResponseEntity.status(404).body(null);
            }

            Purchase newPurchase = new Purchase();
            newPurchase.setMedicine(lastMedicine);
            newPurchase.setQuantity(quantity);
            newPurchase.setPrice(price);
            newPurchase.setPurchaseDate(new Date(System.currentTimeMillis()));

            newPurchase.setSupplier(lastSupplier);

            Purchase PurchaseCreated = purchaseService.createPurchase(newPurchase);
            return ResponseEntity.status(201).body(PurchaseCreated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Integer id, @RequestBody Purchase purchase) {
        try {
            Purchase purchaseUpdated = purchaseService.updatePurchase(id, purchase);
            return ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
        try {
            purchaseService.deletePurchase(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
