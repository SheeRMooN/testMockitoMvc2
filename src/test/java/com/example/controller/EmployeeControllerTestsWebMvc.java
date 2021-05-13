package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.exception.EmployeeNotFoundException;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTestsWebMvc {
 
    @MockBean
    EmployeeService employeeService;
 
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
 
    @Test
    public void testfindAll() throws Exception {
        Employee employee = new Employee("Lokesh1", "Gupta1");
        Employee employee1 = new Employee("Lokesh2", "Gupta2");
        List<Employee> employees = Arrays.asList(employee,employee1);
        when(employeeService.findAll()).thenReturn(employees);
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh1")))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Gupta1")))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Lokesh2")))
                .andExpect(jsonPath("$[1].lastName", Matchers.is("Gupta2")))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(employee, employee1))));
    }

    @Test
    public void testCreate() throws Exception{
        Employee employee = new Employee(1,"name", "nick");
        when(employeeService.save(employee)).thenReturn(employee);
        mockMvc.perform(
                put("/employee")
                .content(objectMapper.writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.firstName",Matchers.is("name")))
                .andExpect(jsonPath("$.lastName",Matchers.is("nick")))
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));
    }
    @Test
    public void testDelete() throws Exception{
        mockMvc.perform(delete("/employee/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void testDelete2()throws Exception{
        Employee employee = new Employee(3,"str4","str5");
        when(employeeService.findById(employee.getId())).thenReturn(Optional.of(employee));
        doNothing().when(employeeService).deleteById(employee.getId());

        mockMvc.perform(
                delete("/employee/{id}", employee.getId()))
                .andExpect(status().isOk());

    }
    @Test
    void findByIdExc() throws Exception {
        int id = 10;
        when(employeeService.findById(id)).thenThrow(new EmployeeNotFoundException("No employee find"));

        mockMvc.perform(get("/emloyee/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("INCORRECT_REQUEST")))
                .andExpect(jsonPath("$.details[0]", is("No employee find")));
    }
}