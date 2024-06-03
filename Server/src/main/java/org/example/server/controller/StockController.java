package org.example.server.controller;

import org.example.server.entity.Department;
import org.example.server.entity.Medicine;
import org.example.server.entity.Stock;
import org.example.server.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospital_stocks/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

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

    @PostMapping
    public ResponseEntity<Stock> createStock(
            @RequestParam Integer quantity,
            @RequestParam String departmentName
    ) {
        try {
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
