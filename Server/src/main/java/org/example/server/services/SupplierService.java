package org.example.server.services;

import org.example.server.entity.Supplier;
import org.example.server.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        try {
            return supplierRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all suppliers", e);
        }
    }

    public Supplier getSupplierById(Integer id) {
        try {
            Optional<Supplier> supplier = supplierRepository.findById(id);
            return supplier.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting supplier by id", e);
        }
    }

    public Supplier createSupplier(Supplier supplier) {
        try {
            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new RuntimeException("Error creating supplier", e);
        }
    }

    public Supplier updateSupplier(Integer id, Supplier supplierInfo) {
        try {
            Supplier supplier = supplierRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Supplier not found by id " + id));

            if (supplierInfo.getName() != null) {
                supplier.setName(supplierInfo.getName());
            }
            if (supplierInfo.getContactInfo() != null) {
                supplier.setContactInfo(supplierInfo.getContactInfo());
            }

            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new RuntimeException("Error updating supplier", e);
        }
    }

    public void deleteSupplier(Integer id) {
        try {
            supplierRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting supplier", e);
        }
    }
}
