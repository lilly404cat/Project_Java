package org.example.server.controller;

import org.example.server.entity.Consumption;
import org.example.server.entity.Department;
import org.example.server.entity.Medicine;
import org.example.server.entity.Stock;
import org.example.server.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hospital_stocks/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * method to get all stocks
     * @return list response
     */
    @GetMapping
    public ResponseEntity<List<Stock>> getStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * request to get stock by id
     * @param id to be used in
     * @return response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        try {
            Stock stock = stockService.getStockById(id);
            if (stock != null) {
                return ResponseEntity.ok(stock);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to create a stock
     * @param quantity to be set
     * @param departmentName to be set
     * @return response
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(
            @RequestParam Integer quantity,
            @RequestParam String departmentName
    ) {
        try {
            System.out.println(quantity + " " + departmentName);
            Medicine lastMedicine = stockService.findLastEnteredMedicine();
            if (lastMedicine == null) {
                return ResponseEntity.status(404).body(null);
            }

            Department department = stockService.findDepartmentByName(departmentName);
            if (department == null) {
                return ResponseEntity.status(404).body(null);
            }

            Stock newStock = new Stock();
            newStock.setMedicine(lastMedicine);
            newStock.setDepartment(department);
            newStock.setQuantity(quantity);
            newStock.setLastUpdated(new Timestamp(System.currentTimeMillis()));

            Stock stockCreated = stockService.createStock(newStock);
            return ResponseEntity.status(201).body(stockCreated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to update stock quantity
     * @param id to be used to find the stock to be updated
     * @param quantity to be updated
     * @return response
     */
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<Stock> updateConsumptionQuantity(
            @PathVariable Integer id,
            @RequestParam Integer quantity
    ) {
        try {
            System.out.println(id +  quantity);
            Stock updatedStock= stockService.updateStockQuantity(id, quantity);
            return ResponseEntity.ok(updatedStock);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * method to find by department and drug
     * @param medicine to be used
     * @param department to be used
     * @return response
     */
    @GetMapping("/findIdsByMedicineAndDepartment")
    public ResponseEntity<Integer> getStockIdsByMedicineAndDepartment(
            @RequestParam String medicine,
            @RequestParam String department
    ) {
        try {
            System.out.println(department + " " + medicine);
            Integer consumptionIds = stockService.findConsumptionIdsByMedicineAndDepartment(medicine, department);

            return ResponseEntity.ok(consumptionIds);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to update stock
     * @param id to be found by
     * @param stock to be updated
     * @return response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stock) {
        try {
            Stock stockUpdated = stockService.updateStock(id, stock);
            return ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * delete request for a stock
     * @param id to find the stock to delete
     * @return response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        try {
            stockService.deleteStock(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * request to get the medicine quantities by department
     * @param departmentId  to be used in query
     * @return response
     */
    @GetMapping("/department/{departmentId}/medicines")
    public ResponseEntity<Map<String, Integer>> getMedicineQuantitiesByDepartment(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> medicineQuantities = stockService.getMedicineQuantitiesByDepartment(departmentId);
            return ResponseEntity.ok(medicineQuantities);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
