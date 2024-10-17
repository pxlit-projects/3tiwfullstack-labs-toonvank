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

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setName(updatedEmployee.getName());
                    existingEmployee.setAge(updatedEmployee.getAge());
                    existingEmployee.setPosition(updatedEmployee.getPosition());
                    return employeeRepository.save(existingEmployee);
                })
                .orElse(null);
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