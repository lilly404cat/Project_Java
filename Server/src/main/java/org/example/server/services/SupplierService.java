package org.example.server.services;

import org.example.server.entity.Supplier;
import org.example.server.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * the SupplierService class
 */
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * gets all the suppliers
     * @return List
     */
    public List<Supplier> getAllSuppliers() {
        try {
            return supplierRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all suppliers", e);
        }
    }

    /**
     * gets a supplier by its id
     * @param id the id
     * @return Supplier
     */
    public Supplier getSupplierById(Integer id) {
        try {
            Optional<Supplier> supplier = supplierRepository.findById(id);
            return supplier.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting supplier by id", e);
        }
    }

    /**
     * creates a new supplier
     * @param supplier the supplier
     * @return Supplier
     */
    public Supplier createSupplier(Supplier supplier) {
        try {
            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new RuntimeException("Error creating supplier", e);
        }
    }

    /**
     * updates a supplier
     * @param id the id
     * @param supplierInfo the supplier object
     * @return Supplier
     */
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

    /**
     * deletes a supplier
     * @param id the id
     */
    public void deleteSupplier(Integer id) {
        try {
            supplierRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting supplier", e);
        }
    }

    /**
     * finds a supplier by a name
     * @param name the name
     * @return Integer
     */
    public Integer findByName(String name) {
        try {
            Supplier supplier = supplierRepository.findByName(name);
            return supplier != null ? supplier.getId() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting department by name", e);
        }
    }

}
