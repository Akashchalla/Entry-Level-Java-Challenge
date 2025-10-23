package com.challenge.api.controller;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST Controller for Employee management operations. Provides endpoints for retrieving and
 * creating employees. This controller acts as the interface between the Employees-R-US SaaS
 * platform and the existing employee management solution.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor injection of EmployeeService dependency.
     *
     * @param employeeService Service layer for employee operations
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieves all employees from the system.
     *
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee
     *     models as necessary.
     * @return List of all employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Retrieves a specific employee by their UUID.
     *
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee
     *     model as necessary.
     * @param uuid Employee UUID from the path variable
     * @return Requested Employee if exists
     * @throws ResponseStatusException with 404 status if employee not found
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee =
                employeeService
                        .getEmployeeByUuid(uuid)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Employee not found with UUID: " + uuid));
        return ResponseEntity.ok(employee);
    }

    /**
     * Creates a new employee in the system.
     *
     * @implNote Need not be concerned with an actual persistence layer.
     * @param requestBody DTO containing employee creation data
     * @return Newly created Employee with generated UUID
     * @throws ResponseStatusException with 400 status if validation fails
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest requestBody) {
        try {
            // Map DTO to Employee entity
            Employee employee = new EmployeeImpl();
            employee.setFirstName(requestBody.getFirstName());
            employee.setLastName(requestBody.getLastName());
            employee.setSalary(requestBody.getSalary());
            employee.setAge(requestBody.getAge());
            employee.setJobTitle(requestBody.getJobTitle());
            employee.setEmail(requestBody.getEmail());

            // Create employee through service
            Employee createdEmployee = employeeService.createEmployee(employee);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
