package com.example.controller;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepo;

    @AfterEach
    void tearDown() {
        employeeRepo.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        Employee employee1 = create("first", "name");
        Employee employee2 = create("second", "name");

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(employee1, employee2))));

        deleteEmployee(employee1);
        deleteEmployee(employee2);
    }

    @Test
    void findById() throws Exception {
        Employee employee = create("first","name");
        int id = employee.getId();

        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("first"));
//        deleteEmployee(employee);

//        Employee employee2 = create("second", "name");
//        mockMvc.perform(get("/employee{id}", employee2.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(employee2)));
//        deleteEmployee(employee2);
    }

    @Test
    void save() throws Exception {
        Employee employee = create("test","name");

        mockMvc.perform(post("/employee")
                .content(objectMapper.writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("test"))
                .andExpect(jsonPath("$.lastName").value("name"));
        deleteEmployee(employee);
    }
    private Employee create(String firstName, String lastName) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return employeeRepo.save(employee);
    }
    private void deleteEmployee(Employee employee){
        employeeRepo.deleteById(employee.getId());
    }
}