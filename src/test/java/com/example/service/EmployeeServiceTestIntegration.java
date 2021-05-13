package com.example.service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class EmployeeServiceTestIntegration {
    @Autowired
    EmployeeService service;
    @Autowired
    EmployeeRepository repository;


    @Test
    public void ff(){
        Employee employee = new Employee(3,"111","222");
        repository.save(employee);
        assertEquals(2,service.findAll().size());
    }
}