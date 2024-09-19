package com.seshwoods.controller;

import com.seshwoods.domain.Organization;
import com.seshwoods.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) {
        Organization Organization = organizationService.getOrganizationById(id);
        return Organization != null ? ResponseEntity.ok(Organization) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization Organization) {
        Organization createdOrganization = organizationService.createOrganization(Organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id, @RequestBody Organization Organization) {
        Organization updatedOrganization = organizationService.updateOrganization(id, Organization);
        return updatedOrganization != null ? ResponseEntity.ok(updatedOrganization) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}