package org.example.server.controller;

import org.example.server.entity.Department;
import org.example.server.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital_stocks/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * get request to get the departments all
     * @return a response
     */
    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * get by id request a department
     * @param id to be used in the request then in query
     * @return response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            if (department != null) {
                return ResponseEntity.ok(department);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * request post to craete a new depafrtment
     * @param department to  be set
     * @return response
     */
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        try {
            Department departmentCreated = departmentService.createDepartment(department);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * request to update the department
     * @param id to be used to update the department
     * @param department to be uupdated with
     * @return response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        try {
            Department departmentUpdated = departmentService.updateDepartment(id, department);
            return ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * map request to delete a department by name
     * @param name to be used to delete a department
     * @return response(nothing)
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String name) {
        try {
            Integer id = departmentService.findByName(name);
            if (id != null) {
                departmentService.deleteDepartment(id);
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

    /**
     * request to find if a department exists
     * @param departmentId to be used in request in searching
     * @return response
     */
    @RequestMapping(value = "/exists/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> checkDepartment(@PathVariable Integer departmentId) {
        boolean exists = departmentService.existsById(departmentId);
        if (exists) {
            return ResponseEntity.ok(200);
        } else {
            return ResponseEntity.ok(304);
        }
    }
}
