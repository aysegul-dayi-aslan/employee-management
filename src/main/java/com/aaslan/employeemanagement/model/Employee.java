package com.aaslan.employeemanagement.model;


import com.aaslan.employeemanagement.dto.EmployeeDto;
import com.aaslan.employeemanagement.utils.DateUtils;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employee")
@Data
@Accessors(chain = true)
@FieldNameConstants
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @LastModifiedDate
    private Date updated = DateUtils.now();

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "salary")
    private float salary;

    @Column(name = "employment")
    private Date employment;

    @Column(name = "leave")
    private Date leave;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    public void set(EmployeeDto employeeDto, Department department) {
        this.setName(StringUtils.isBlank(employeeDto.getName()) ? this.getName() : employeeDto.getName())
                .setSurname(StringUtils.isBlank(employeeDto.getSurname()) ? this.getSurname() : employeeDto.getSurname())
                .setSalary(employeeDto.getSalary() <= 0 ? this.getSalary() : employeeDto.getSalary())
                .setEmployment(Objects.isNull(employeeDto.getEmployment()) ? this.getEmployment() : employeeDto.getEmployment())
                .setLeave(Objects.isNull(employeeDto.getLeave()) ? this.getLeave() : employeeDto.getLeave())
                .setDepartment(department);
    }
}
