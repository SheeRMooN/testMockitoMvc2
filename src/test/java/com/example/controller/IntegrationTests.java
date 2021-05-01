package com.example.controller;

import javax.validation.ValidationException;

import com.example.controller.EmployeeController;
import com.example.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 

@ExtendWith(SpringExtension.class)

@WebMvcTest
public class IntegrationTests {
 
    @MockBean
    EmployeeController employeeController;

    @Test
    public void testCreateReadDelete() {
        Employee employee = new Employee("Lokesh", "Gupta");
 
        Employee employeeResult = employeeController.create(employee);
 
        Iterable<Employee> employees = employeeController.read();
        Assertions.assertThat(employees).first().hasFieldOrPropertyWithValue("firstName", "Lokesh");
 
        employeeController.delete(employeeResult.getId());
        Assertions.assertThat(employeeController.read()).isEmpty();
    }
 
    @Test
    public void errorHandlingValidationExceptionThrown() {
 
        Assertions.assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> employeeController.somethingIsWrong());
    }
}