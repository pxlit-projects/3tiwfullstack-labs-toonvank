package com.seshwoods.service;

import com.seshwoods.domain.Department;
import com.seshwoods.exception.ResourceNotFoundException;
import com.seshwoods.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
    }

    public Department updateDepartment(Long departmentId, Department departmentDetails) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        department.setOrganizationId(departmentDetails.getOrganizationId());
        department.setName(departmentDetails.getName());
        department.setEmployees(departmentDetails.getEmployees());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        departmentRepository.delete(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}