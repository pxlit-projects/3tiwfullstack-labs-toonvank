package com.seshwoods;

import com.seshwoods.domain.Employee;
import com.seshwoods.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {
    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();  // Clear all records from the repository
    }
    @Autowired
    MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private EmployeeRepository employeeRepository;
    @Container
    private static MySQLContainer sqlContainer =
            new MySQLContainer("mysql:5.7.34");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1,employeeRepository.findAll().size());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = employeeRepository.save(Employee.builder()
                .age(30)
                .name("Alice")
                .position("Manager")
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice")))
                .andExpect(jsonPath("$.position", is("Manager")));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Ensure the repository starts with no employees
        employeeRepository.deleteAll();

        // Add a few employees to the repository
        employeeRepository.save(Employee.builder()
                .age(30)
                .name("Alice")
                .position("Manager")
                .build());

        employeeRepository.save(Employee.builder()
                .age(22)
                .name("Chris")
                .position("Intern")
                .build());

        // Make a GET request to retrieve all employees
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))  // Verify the number of employees
                .andExpect(jsonPath("$[0].name", org.hamcrest.Matchers.is("Alice")))
                .andExpect(jsonPath("$[1].name", org.hamcrest.Matchers.is("Chris")));
    }


    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = employeeRepository.save(Employee.builder()
                .age(22)
                .name("Chris")
                .position("Intern")
                .build());

        Employee updatedEmployee = Employee.builder()
                .age(23)
                .name("Chris")
                .position("Junior Developer")
                .build();

        String updatedEmployeeString = objectMapper.writeValueAsString(updatedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(updatedEmployeeString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position", is("Junior Developer")));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Employee employee = employeeRepository.save(Employee.builder()
                .age(40)
                .name("David")
                .position("Senior Engineer")
                .build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/" + employee.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, employeeRepository.findAll().size());
    }
}
