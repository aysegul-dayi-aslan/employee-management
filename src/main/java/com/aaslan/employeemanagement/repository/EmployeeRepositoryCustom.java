package com.aaslan.employeemanagement.repository;

import com.aaslan.employeemanagement.dto.EmployeeFilter;
import com.aaslan.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryCustom {

    Page<Employee> findAllByFilter(EmployeeFilter filter, Pageable pageable);
}
