package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.stereotype.Service;

/**
 * Service layer for Employee operations. Handles business logic and mock data management.
 * In a production environment, this would interact with a database through a repository layer.
 */
@Service
public class EmployeeService {

    // In-memory storage for mock employees (simulating a database)
    private final Map<UUID, Employee> employeeDatabase = new HashMap<>();

    /**
     * Constructor initializes the service with some mock employee data.
     */
    public EmployeeService() {
        initializeMockData();
    }

    /**
     * Retrieves all employees from the mock database.
     *
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeDatabase.values());
    }

    /**
     * Retrieves a specific employee by their UUID.
     *
     * @param uuid The unique identifier of the employee
     * @return Optional containing the employee if found, empty otherwise
     */
    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(employeeDatabase.get(uuid));
    }

    /**
     * Creates a new employee and adds them to the mock database.
     *
     * @param employee The employee to create (without UUID)
     * @return The created employee with generated UUID
     */
    public Employee createEmployee(Employee employee) {
        // Validate required fields
        validateEmployee(employee);

        // Generate UUID if not already set
        if (employee.getUuid() == null) {
            employee.setUuid(UUID.randomUUID());
        }

        // Set hire date if not already set
        if (employee.getContractHireDate() == null) {
            employee.setContractHireDate(Instant.now());
        }

        // Set full name if not already set
        if (employee.getFullName() == null
                && employee.getFirstName() != null
                && employee.getLastName() != null) {
            employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
        }

        // Store in mock database
        employeeDatabase.put(employee.getUuid(), employee);

        return employee;
    }

    /**
     * Validates that an employee has all required fields.
     *
     * @param employee The employee to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateEmployee(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new IllegalArgumentException("Valid salary is required");
        }
        if (employee.getAge() == null || employee.getAge() < 18 || employee.getAge() > 120) {
            throw new IllegalArgumentException("Valid age is required (18-120)");
        }
    }

    /**
     * Initializes the mock database with sample employee data.
     */
    private void initializeMockData() {
        // Create sample employees
        Employee emp1 = createSampleEmployee(
                "John",
                "Doe",
                75000,
                30,
                "Software Engineer",
                "john.doe@company.com",
                Instant.now().minus(730, ChronoUnit.DAYS));

        Employee emp2 = createSampleEmployee(
                "Jane",
                "Smith",
                95000,
                35,
                "Senior Software Engineer",
                "jane.smith@company.com",
                Instant.now().minus(1460, ChronoUnit.DAYS));

        Employee emp3 = createSampleEmployee(
                "Michael",
                "Johnson",
                120000,
                42,
                "Engineering Manager",
                "michael.johnson@company.com",
                Instant.now().minus(2190, ChronoUnit.DAYS));

        Employee emp4 = createSampleEmployee(
                "Emily",
                "Williams",
                68000,
                28,
                "Junior Developer",
                "emily.williams@company.com",
                Instant.now().minus(365, ChronoUnit.DAYS));

        Employee emp5 = createSampleEmployee(
                "David",
                "Brown",
                85000,
                33,
                "DevOps Engineer",
                "david.brown@company.com",
                Instant.now().minus(1095, ChronoUnit.DAYS));

        // Add to database
        employeeDatabase.put(emp1.getUuid(), emp1);
        employeeDatabase.put(emp2.getUuid(), emp2);
        employeeDatabase.put(emp3.getUuid(), emp3);
        employeeDatabase.put(emp4.getUuid(), emp4);
        employeeDatabase.put(emp5.getUuid(), emp5);
    }

    /**
     * Helper method to create a sample employee.
     */
    private Employee createSampleEmployee(
            String firstName,
            String lastName,
            Integer salary,
            Integer age,
            String jobTitle,
            String email,
            Instant hireDate) {
        Employee employee = new EmployeeImpl(firstName, lastName, salary, age, jobTitle, email);
        employee.setUuid(UUID.randomUUID());
        employee.setContractHireDate(hireDate);
        return employee;
    }
}
