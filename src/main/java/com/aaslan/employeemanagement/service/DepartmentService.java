package com.aaslan.employeemanagement.service;

import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.repository.DepartmentRepository;
import com.aaslan.employeemanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public ResponseEntity<Page<Department>> list(int page, int size, Location location) {
        Date start = DateUtils.now();
        Page<Department> departments;
        if (Objects.isNull(location)) {
            departments = departmentRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, Employee.Fields.name));
        } else {
            departments = departmentRepository.findAllByLocation(location, PageRequest.of(page, size, Sort.Direction.ASC, Employee.Fields.name));
        }

        log.info("Department list query has been completed in {} milliseconds", DateUtils.difference(start));
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElse(null);
    }
}
