package com.aaslan.employeemanagement.service;

import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.repository.DepartmentRepository;
import com.aaslan.employeemanagement.repository.EmployeeRepository;
import com.aaslan.employeemanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @PostConstruct
    public void init() {
        if (CollectionUtils.isEmpty(departmentRepository.findAll())) {
            addDepartment();
        }

        if (CollectionUtils.isEmpty(employeeRepository.findAll())) {
            addEmployee();
        }
    }

    private void addEmployee() {
        Employee employee = new Employee();
        employee.setName("John")
                .setSurname("Doe")
                .setDepartment(departmentRepository.findByName("Administrative"))
                .setSalary(100000)
                .setEmployment(DateUtils.minus(ChronoUnit.YEARS, 1));
        employeeRepository.save(employee);

        Employee employee1 = new Employee();
        employee1.setName("Jane")
                .setSurname("Smith")
                .setDepartment(departmentRepository.findByName("Service Development"))
                .setSalary(60000)
                .setEmployment(DateUtils.minus(ChronoUnit.MONTHS, 9))
                .setLeave(DateUtils.minus(ChronoUnit.DAYS, 2));
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Emily")
                .setSurname("Davis")
                .setDepartment(departmentRepository.findByName("IT"))
                .setSalary(80000)
                .setEmployment(DateUtils.minus(ChronoUnit.YEARS, 2));
        employeeRepository.save(employee2);
    }

    private void addDepartment() {
        Department administrative = new Department();
        administrative.setName("Administrative")
                .setLocation(Location.UNITED_STATES);
        departmentRepository.save(administrative);

        Department hr = new Department();
        hr.setName("HR")
                .setLocation(Location.UNITED_KINGDOM);
        departmentRepository.save(hr);

        Department sd = new Department();
        sd.setName("Service Development")
                .setLocation(Location.TURKEY);
        departmentRepository.save(sd);

        Department it = new Department();
        it.setName("IT")
                .setLocation(Location.GERMANY);
        departmentRepository.save(it);

        Department finance = new Department();
        finance.setName("Finance")
                .setLocation(Location.FRANCE);
        departmentRepository.save(finance);
    }
}
