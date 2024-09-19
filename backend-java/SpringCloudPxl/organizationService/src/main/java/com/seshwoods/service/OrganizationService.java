package com.seshwoods.service;

import com.seshwoods.domain.Organization;
import com.seshwoods.exception.ResourceNotFoundException;
import com.seshwoods.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public Organization getOrganizationById(Long OrganizationId) {
        return organizationRepository.findById(OrganizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + OrganizationId));
    }

    public Organization updateOrganization(Long OrganizationId, Organization OrganizationDetails) {
        Organization Organization = organizationRepository.findById(OrganizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + OrganizationId));

        return organizationRepository.save(Organization);
    }

    public void deleteOrganization(Long OrganizationId) {
        Organization Organization = organizationRepository.findById(OrganizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + OrganizationId));
        organizationRepository.delete(Organization);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }
}