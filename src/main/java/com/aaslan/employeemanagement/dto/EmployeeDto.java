package com.aaslan.employeemanagement.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class EmployeeDto {
    private Long id;
    private String name;
    private String surname;
    private float salary;
    private Date employment;
    private Date leave;
    private Long departmentId;
}
