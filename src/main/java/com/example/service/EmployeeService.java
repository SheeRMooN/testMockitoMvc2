package com.example.service;

import com.example.exception.EmployeeNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeService {

	EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		if (employee.getId() == null) {
			employee = employeeRepository.save(employee);
			return employee;
		} else {
			Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
			if (employeeOptional.isPresent()) {
				Employee newEntity = employeeOptional.get();
				newEntity.setFirstName(employee.getFirstName());
				newEntity.setLastName(employee.getLastName());
				newEntity = employeeRepository.save(newEntity);
				return newEntity;
			} else {
				throw new EmployeeNotFoundException("No employee record exist for given id");
			}
		}
	}
	public List<Employee> findAll() {
		List<Employee> result = (List<Employee>) employeeRepository.findAll();
		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Employee>();
		}
	}
	public void deleteById(Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
		} else {
			throw new EmployeeNotFoundException("No employee record exist for given id");
		}
	}
	void deleteAll() {
		employeeRepository.deleteAll();
	}

	public Optional<Employee> findById(Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()){
			return employee;
		}else {
			throw new EmployeeNotFoundException("No employee find");
		}
	}
}