package com.seshwoods;

import com.seshwoods.domain.Department;
import com.seshwoods.repository.DepartmentRepository;
import com.seshwoods.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.34");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = Department.builder()
                .name("HR")
                .build();
        departmentRepository.save(department);

        mockMvc.perform(get("/department/{id}", department.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void testGetDepartmentByIdNotFound() throws Exception {
        mockMvc.perform(get("/department/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        List<Department> departments = Arrays.asList(
                Department.builder().name("HR").build(),
                Department.builder().name("Finance").build()
        );
        departmentRepository.saveAll(departments);

        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("Finance"));
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = Department.builder()
                .name("HR")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(post("/department")
                        .contentType("application/json")
                        .content(departmentString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        Department department = Department.builder()
                .name("HR")
                .build();
        departmentRepository.save(department);

        Department updatedDepartment = Department.builder()
                .name("IT")
                .build();

        String updatedDepartmentString = objectMapper.writeValueAsString(updatedDepartment);

        mockMvc.perform(put("/department/{id}", department.getId())
                        .contentType("application/json")
                        .content(updatedDepartmentString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Department department = Department.builder()
                .name("HR")
                .build();
        departmentRepository.save(department);

        mockMvc.perform(delete("/department/{id}", department.getId()))
                .andExpect(status().isNoContent());
    }
}
