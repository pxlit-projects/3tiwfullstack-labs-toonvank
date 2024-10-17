package com.seshwoods.controller;

import com.seshwoods.client.NotificationClient;
import com.seshwoods.domain.Employee;
import com.seshwoods.dto.NotifcationRequest;
import com.seshwoods.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final NotificationClient notifcationClient;

    public EmployeeController(EmployeeService employeeService, NotificationClient notifcationClient) {
        this.employeeService = employeeService;
        this.notifcationClient = notifcationClient;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee Employee = employeeService.getEmployeeById(id);
        return Employee != null ? ResponseEntity.ok(Employee) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void createEmployee(@RequestBody Employee Employee) {
        Employee createdEmployee = employeeService.createEmployee(Employee);

        NotifcationRequest notifcationRequest =
                NotifcationRequest.builder().message("Employee Created")
                        .sender("Toon")
                                .build();

        notifcationClient.sendNotification(notifcationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee Employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, Employee);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}