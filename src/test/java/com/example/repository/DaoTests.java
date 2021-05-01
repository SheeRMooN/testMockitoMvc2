package com.example.repository;

import com.example.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testCreateReadDelete() {
        Employee employee = new Employee("Lokesh", "Gupta");

        employeeRepository.save(employee);

        Iterable<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees).extracting(Employee::getFirstName).containsOnly("Lokesh");

        employeeRepository.deleteAll();
        Assertions.assertThat(employeeRepository.findAll()).isEmpty();
    }
}