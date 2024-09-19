package com.seshwoods.service;

import com.seshwoods.domain.Employee;
import com.seshwoods.exception.ResourceNotFoundException;
import com.seshwoods.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository EmployeeRepository) {
        this.employeeRepository = EmployeeRepository;
    }
    public Employee createEmployee(Employee Employee) {
        return employeeRepository.save(Employee);
    }

    public Employee getEmployeeById(Long EmployeeId) {
        return employeeRepository.findById(EmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + EmployeeId));
    }

    public Employee updateEmployee(Long EmployeeId, Employee EmployeeDetails) {
        Employee Employee = employeeRepository.findById(EmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + EmployeeId));
        Employee.setOrganizationId(EmployeeDetails.getOrganizationId());

        return employeeRepository.save(Employee);
    }

    public void deleteEmployee(Long EmployeeId) {
        Employee Employee = employeeRepository.findById(EmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + EmployeeId));
        employeeRepository.delete(Employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}