package org.example.server.controller;

import org.example.server.entity.Supplier;
import org.example.server.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital_stocks/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * method to get all suppliers
     * @return response
     */
    @GetMapping
    public ResponseEntity<List<Supplier>> getSuppliers() {
        try {
            List<Supplier> suppliers = supplierService.getAllSuppliers();
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to get a supplier by id
     * @param id to be used to get a supplier
     * @return response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        try {
            Supplier supplier = supplierService.getSupplierById(id);
            if (supplier != null) {
                return ResponseEntity.ok(supplier);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to create a supplier
     * @param supplier to be created
     * @return response
     */
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        try {
            System.out.println(supplier);
            Supplier supplierCreated = supplierService.createSupplier(supplier);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        try {
            Supplier supplierUpdated = supplierService.updateSupplier(id, supplier);
            return ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to delete a supplier by name
     * @param name to be used in delete
     * @return response
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String name) {
        try {
            System.out.println(name);
            Integer id = supplierService.findByName(name);
            if (id != null) {
                supplierService.deleteSupplier(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
