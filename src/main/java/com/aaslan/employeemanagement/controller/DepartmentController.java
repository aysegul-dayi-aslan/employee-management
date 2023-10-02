package com.aaslan.employeemanagement.controller;

import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Department Operations")
@RequestMapping("/api/v1/department")
@Slf4j
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping()
    @ApiOperation(value = "List departments with filters that page, size and location.")
    public ResponseEntity<Page<Department>> list(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                 @RequestParam(value = "location", required = false) Location location) {
        log.info("Department audit's record request has been started.");
        return departmentService.list(page, size, location);
    }
}
