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

/**
 * the StockService class
 */
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * returns a list with all the stocks
     * @return List
     */
    public List<Stock> getAllStocks() {
        try {
            return stockRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all stocks", e);
        }
    }

    /**
     * gets a stock by an id
     * @param id the id
     * @return Stock
     */
    public Stock getStockById(Integer id) {
        try {
            Optional<Stock> stock = stockRepository.findById(id);
            return stock.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting stock by id", e);
        }
    }

    /**
     * creates a new stock
     * @param stock the stock
     * @return Stock
     */
    public Stock createStock(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Error creating stock", e);
        }
    }

    /**
     * updates a stock
     * @param id the id
     * @param stockInfo the stock object
     * @return Stock
     */
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

    /**
     * deletes a stock
     * @param id the id
     */
    public void deleteStock(Integer id) {
        try {
            stockRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting stock", e);
        }
    }

    /**
     * returns the stocks of medicines for a specific department
     * @param departmentId the id
     * @return Map
     */
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
        return medicineRepository.findTopByOrderByIdDesc();
    }

    /**
     * finds a department by its name
     * @param name the name
     * @return Department
     */
    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    /**
     * updates the quantity of a stock
     * @param id the id
     * @param quantity the quantity
     * @return Stock
     */
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

    /**
     * returns the id of a consumption using the id of the medicine and the id of the department
     * @param medicineName the name
     * @param departmentName the name
     * @return Integer
     */
    public Integer findConsumptionIdsByMedicineAndDepartment(String medicineName, String departmentName) {
        return stockRepository.findIdsByMedicineNameAndDepartmentName(medicineName, departmentName);
    }
}
