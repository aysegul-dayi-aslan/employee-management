package com.aaslan.employeemanagement.controller;

import com.aaslan.employeemanagement.dto.EmployeeDto;
import com.aaslan.employeemanagement.dto.EmployeeFilter;
import com.aaslan.employeemanagement.model.Audit;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.service.AuditService;
import com.aaslan.employeemanagement.service.EmployeeService;
import com.aaslan.employeemanagement.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Employee Operations")
@RequestMapping("/api/v1/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuditService auditService;

    @PostMapping("persist")
    @ApiOperation(value = "Add employee with credentials.")
    public ResponseEntity<String> persist(@RequestBody EmployeeDto employeeDto) {
        log.info("Employee persist request has been started.");
        return employeeService.persist(employeeDto);
    }

    @PostMapping("update/{id}")
    @ApiOperation(value = "Update employee.")
    public ResponseEntity<String> update(@PathVariable(value = "id") Long id, @RequestBody EmployeeDto employeeDto) {
        log.info("Employee update request has been started.");
        return employeeService.update(id, employeeDto);
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "Delete employee.")
    public ResponseEntity<String> delete(@RequestParam(value = "id", required = true) Long id) {
        log.info("Employee delete request has been started.");
        return employeeService.delete(id);
    }

    @PostMapping("list")
    @ApiOperation(value = "List employee by filters includes employee information's.")
    public ResponseEntity<Page<Employee>> list(@RequestBody EmployeeFilter employeeFilter,
                                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("Employee list by filters request has been started.");
        return employeeService.list(employeeFilter, page, size);
    }

    @GetMapping("list/{location}")
    @ApiOperation(value = "List employee by location.")
    public ResponseEntity<Page<Employee>> listByLocation(@PathVariable(value = "location") Location location,
                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("Employee list by location request has been started.");
        return employeeService.listByLocation(location, page, size);
    }

    @GetMapping("audit/{id}")
    @ApiOperation(value = "List employee audit records.")
    public ResponseEntity<Page<Audit>> audit(@PathVariable(value = "id") Long id,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("Employee audit's logs request has been started.");
        return auditService.audits(id, page, size);
    }
}
