package com.aaslan.employeemanagement;

import com.aaslan.employeemanagement.model.Audit;
import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.model.Location;
import com.aaslan.employeemanagement.repository.AuditRepository;
import com.aaslan.employeemanagement.repository.DepartmentRepository;
import com.aaslan.employeemanagement.repository.EmployeeRepository;
import com.aaslan.employeemanagement.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeManagementApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Test
    void contextLoads() {
    }

    /**
     * Insert new employee
     */
    @Test
    public void saveEmployee() {
        Employee employee = new Employee();
        employee.setName("Jane")
                .setSurname("Austin")
                .setEmployment(DateUtils.now())
                .setSalary(100000)
                .setDepartment(departmentRepository.findAll().stream().findFirst().orElse(null));

        Employee saved = employeeRepository.save(employee);
        assert Objects.equals(saved.getName(), employee.getName());
    }

    /**
     * Find and update employee
     */
    @Test
    public void updateEmployee() {
        Employee employee = employeeRepository.findAll().stream().findFirst().orElse(null);
        assert employee != null;
        employee.setLeave(DateUtils.plus(ChronoUnit.MONTHS, 1));

        Employee updated = employeeRepository.save(employee);
        assert Objects.equals(updated.getLeave(), employee.getLeave());
    }

    /**
     * Find and delete employee
     */
    @Test
    public void deleteEmployee() {
        Employee employee = employeeRepository.findAll().stream().findFirst().orElse(null);
        assert employee != null;

        employeeRepository.delete(employee);
        Employee deleted = employeeRepository.findById(employee.getId()).orElse(null);
        assertNull(deleted);
    }

    /**
     * Update employee department, create new department, write new audit for update
     */
    @Test
    public void changeRelation() {
        Employee employee = employeeRepository.findAll().stream().findFirst().orElse(null);
        assert employee != null;

        Department department = new Department();
        department.setLocation(Location.ISTANBUL)
                        .setName("Social Media");
        Department savedDep = departmentRepository.save(department);
        assertEquals(savedDep.getName(), department.getName());

        employee.setDepartment(departmentRepository.findByName("Social Media"));
        Employee savedEmp = employeeRepository.save(employee);
        assertEquals(savedEmp.getName(), employee.getName());

        Audit audit = new Audit();
        audit.setEmployeeId(employee.getId())
                .setOldDeparment(employee.getDepartment().getName())
                .setNewDepartment(department.getName());

        Audit savedAudit = auditRepository.save(audit);
        assertEquals(savedAudit.getEmployeeId(), employee.getId());
    }
}
