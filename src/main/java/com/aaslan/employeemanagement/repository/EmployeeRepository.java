package com.aaslan.employeemanagement.repository;

import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

    Page<Employee> findAllByDepartment_Location(Location location, Pageable pageable);
}
