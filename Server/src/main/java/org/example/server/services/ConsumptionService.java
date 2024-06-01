package org.example.server.services;

import org.example.server.entity.Consumption;
import org.example.server.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionService {

    @Autowired
    private ConsumptionRepository consumptionRepository;

    public List<Consumption> getAllConsumptions() {
        try {
            return consumptionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all consumptions", e);
        }
    }

    public Consumption getConsumptionById(Integer id) {
        try {
            Optional<Consumption> consumption = consumptionRepository.findById(id);
            return consumption.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting consumption by id", e);
        }
    }

    public Consumption createConsumption(Consumption consumption) {
        try {
            return consumptionRepository.save(consumption);
        } catch (Exception e) {
            throw new RuntimeException("Error creating consumption", e);
        }
    }

    public Consumption updateConsumption(Integer id, Consumption consumptionInfo) {
        try {
            Consumption consumption = consumptionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Consumption not found by id " + id));

            if (consumptionInfo.getQuantity() != null) {
                consumption.setQuantity(consumptionInfo.getQuantity());
            }
            if (consumptionInfo.getConsumptionDate() != null) {
                consumption.setConsumptionDate(consumptionInfo.getConsumptionDate());
            }
            if (consumptionInfo.getDepartment() != null) {
                consumption.setDepartment(consumptionInfo.getDepartment());
            }
            if (consumptionInfo.getMedicine() != null) {
                consumption.setMedicine(consumptionInfo.getMedicine());
            }

            return consumptionRepository.save(consumption);
        } catch (Exception e) {
            throw new RuntimeException("Error updating consumption", e);
        }
    }

    public void deleteConsumption(Integer id) {
        try {
            consumptionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting consumption", e);
        }
    }
}
