package org.example.server.services;

import org.example.server.entity.Consumption;
import org.example.server.entity.Department;
import org.example.server.entity.Medicine;
import org.example.server.entity.Stock;
import org.example.server.repository.DepartmentRepository;
import org.example.server.repository.MedicineRepository;
import org.example.server.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Stock> getAllStocks() {
        try {
            return stockRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all stocks", e);
        }
    }

    public Stock getStockById(Integer id) {
        try {
            Optional<Stock> stock = stockRepository.findById(id);
            return stock.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting stock by id", e);
        }
    }

    public Stock createStock(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Error creating stock", e);
        }
    }

    public Stock updateStock(Integer id, Stock stockInfo) {
        try {
            Stock stock = stockRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Stock not found by id " + id));

            if (stockInfo.getMedicine() != null) {
                stock.setMedicine(stockInfo.getMedicine());
            }
            if (stockInfo.getQuantity() != null) {
                stock.setQuantity(stockInfo.getQuantity());
            }
            if (stockInfo.getLastUpdated() != null) {
                stock.setLastUpdated(stockInfo.getLastUpdated());
            }

            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Error updating stock", e);
        }
    }

    public void deleteStock(Integer id) {
        try {
            stockRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting stock", e);
        }
    }

    public Map<String, Integer> getMedicineQuantitiesByDepartment(Integer departmentId) {
        List<Stock> stocks = stockRepository.findByDepartmentId(departmentId);
        Map<String, Integer> medicineQuantities = new HashMap<>();

        for (Stock stock : stocks) {
            String medicineName = stock.getMedicine().getName();
            int quantity = stock.getQuantity();

            medicineQuantities.merge(medicineName, quantity, Integer::sum);        }

        return medicineQuantities;
    }

    public Medicine findLastEnteredMedicine() {
        // Assuming there is a method in medicineRepository to find the last entered medicine
        return medicineRepository.findTopByOrderByIdDesc();
    }

    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    public Stock updateStockQuantity(Integer id, Integer quantity) {
        try{
            Stock stock = stockRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Stock not found by id " + id));
            Integer oldQuantity = stock.getQuantity();
            System.out.println(id);
            if (quantity != null) {
                stock.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
                stock.setQuantity(oldQuantity-quantity);
            }
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Error updating stock", e);
        }
    }
    public Integer findConsumptionIdsByMedicineAndDepartment(String medicineName, String departmentName) {
        return stockRepository.findIdsByMedicineNameAndDepartmentName(medicineName, departmentName);
    }
}
