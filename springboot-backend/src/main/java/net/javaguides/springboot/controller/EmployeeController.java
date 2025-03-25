package net.javaguides.springboot.controller;

import net.javaguides.springboot.exeption.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    // Create employee
    @PostMapping
    public Employee createEmployeeById(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
//build get employee
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
        -> new ResourceNotFoundException("Employee with id " + id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    // Update employee
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable long id, @RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found " + id));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmail_Id(employeeDetails.getEmail_Id()); // Check field name

        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found " + id));

        employeeRepository.delete(employee);
        return ResponseEntity.noContent().build();
    }
}
