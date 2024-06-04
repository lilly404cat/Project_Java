package org.example.server.services;

import org.example.server.entity.Department;
import org.example.server.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * the DepartmentService class
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        try {
            return departmentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all departments", e);
        }
    }

    public Department getDepartmentById(Integer id) {
        try {
            Optional<Department> optionalDepartment = departmentRepository.findById(id);
            return optionalDepartment.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting department by id", e);
        }
    }

    public Department createDepartment(Department department) {
        try {
            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new RuntimeException("Error creating department", e);
        }
    }

    public Department updateDepartment(Integer id, Department department) {
        try {
            Department department1 = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error getting department by id" + id));
            if(department.getName() != null){
                department1.setName(department.getName());
            };
            return departmentRepository.save(department1);
        } catch (Exception e) {
            throw new RuntimeException("Error updating department by id", e);
        }
    }

    public void deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting department by id", e);
        }
    }

    public boolean existsById(Integer departmentId) {
        return departmentRepository.existsById(departmentId);
    }

    public Integer findByName(String name) {
        try {
            Department department = departmentRepository.findByName(name);
            return department != null ? department.getId() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting department by name", e);
        }
    }
}
