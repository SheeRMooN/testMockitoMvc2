package com.example.service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestEmployeeManager {
    @InjectMocks
    EmployeeService service = new EmployeeService();
    @Mock
    EmployeeRepository repository;
//    List<>

    @Test
    public void getAllEmployeesTest()
    {
        List<Employee> list = new ArrayList<Employee>();
        Employee empOne = new Employee(1, "John", "John");
        Employee empTwo = new Employee(2, "Alex", "kolenchiski");
        Employee empThree = new Employee(3, "Steve", "Waugh");
        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);
        when(repository.findAll()).thenReturn(list);
        List<Employee> empList = service.findAll();
        assertEquals(3, empList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getEmployeeByIdTest()
    {
        Employee employee = new Employee(1,"Lokesh","Gupta");
        when(repository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> emp = service.findById(1);

        assertEquals("Lokesh", emp.get().getFirstName());
        assertEquals("Gupta", emp.get().getLastName());
    }

    @Test
    public void createEmployeeTest() {
        Employee emp = new Employee(1,"Lokesh","Gupta");
        service.save(emp);
        verify(repository, times(1)).save(emp);
    }

}