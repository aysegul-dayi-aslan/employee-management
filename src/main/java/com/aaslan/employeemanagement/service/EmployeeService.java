package com.aaslan.employeemanagement.service;

import com.aaslan.employeemanagement.dto.EmployeeDto;
import com.aaslan.employeemanagement.dto.EmployeeFilter;
import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.repository.EmployeeRepository;
import com.aaslan.employeemanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final AuditService auditService;

    public ResponseEntity<String> persist(EmployeeDto employeeDto) {
        Date start = DateUtils.now();
        if (isNotValid(employeeDto)) {
            return new ResponseEntity<>("Missed credentials", HttpStatus.BAD_REQUEST);
        }

        Department department = departmentService.findById(employeeDto.getDepartmentId());
        if (Objects.isNull(department)) {
            return new ResponseEntity<>("Department not found", HttpStatus.NO_CONTENT);
        }

        Employee employee = new Employee();
        employee.set(employeeDto, department);
        employeeRepository.save(employee);
        log.info("Employee persist query has been completed in {} milliseconds", DateUtils.difference(start));
        return new ResponseEntity<>("Employee was saved", HttpStatus.CREATED);
    }

    public ResponseEntity<String> update(Long id, EmployeeDto employeeDto) {
        Date start = DateUtils.now();
        Employee dbEmployee = employeeRepository.findById(id).orElse(null);

        if (Objects.isNull(dbEmployee)) {
            return new ResponseEntity<>("Employee not found", HttpStatus.NO_CONTENT);
        }

        Department department = departmentService.findById(employeeDto.getDepartmentId());
        if (Objects.isNull(department)) {
            return new ResponseEntity<>("Department not found", HttpStatus.NO_CONTENT);
        }

        auditService.auditRecord(dbEmployee, department);

        dbEmployee.set(employeeDto, department);
        employeeRepository.save(dbEmployee);
        log.info("Employee update query has been completed in {} milliseconds", DateUtils.difference(start));
        return new ResponseEntity<>("Employee was updated", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Date start = DateUtils.now();
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (Objects.isNull(employee)) {
            return new ResponseEntity<>("Employee not found", HttpStatus.NO_CONTENT);
        }

        employeeRepository.delete(employee);
        log.info("Employee delete query has been completed in {} milliseconds", DateUtils.difference(start));
        return new ResponseEntity<>("Employee was deleted", HttpStatus.OK);
    }

    public ResponseEntity<Page<Employee>> list(EmployeeFilter employeeFilter, int page, int size) {
        try {
            Date start = DateUtils.now();
            Page<Employee> employees = employeeRepository.findAllByFilter(employeeFilter, PageRequest.of(page, size));
            log.info("Employee list query has been completed in {} milliseconds", DateUtils.difference(start));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error while filtering employees. {} : {}", e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<Employee>> listByLocation(Location location, int page, int size) {
        try {
            Date start = DateUtils.now();
            Page<Employee> employees = employeeRepository.findAllByDepartment_Location(location, PageRequest.of(page, size, Sort.Direction.ASC, Employee.Fields.name));
            log.info("Employee list query has been completed in {} milliseconds", DateUtils.difference(start));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error while filtering employees by location. {} : {}", e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isNotValid(EmployeeDto employeeDto) {
        return Objects.isNull(employeeDto)
                || StringUtils.isBlank(employeeDto.getName())
                || StringUtils.isBlank(employeeDto.getSurname())
                || Objects.isNull(employeeDto.getEmployment())
                || employeeDto.getSalary() <= 0;
    }
}
