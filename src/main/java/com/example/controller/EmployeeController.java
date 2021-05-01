package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.Optional;

@RestController
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;
  @GetMapping("/emloyee/{id}")
  public Optional<Employee> findById(@PathVariable Integer id){
    return employeeService.findById(id);
  }

  @PostMapping("/employee")
  public Employee create(@RequestBody Employee employee)  {
    return employeeService.save(employee);
  }

  @GetMapping("/employee")
  public Iterable<Employee> read() {
    return employeeService.findAll();
  }

  @PutMapping("/employee")
  public Employee update(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }

  @DeleteMapping("/employee/{id}")
  public void delete(@PathVariable Integer id) {
	  employeeService.deleteById(id);
  }

  @GetMapping("/wrong")
  public Employee somethingIsWrong() {
    throw new ValidationException("Something is wrong");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public String exceptionHandler(ValidationException e) {
    return e.getMessage();
  }
}