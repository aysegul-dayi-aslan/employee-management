package com.aaslan.employeemanagement.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Data
@Accessors(chain = true)
public class EmployeeFilter {
    private String name;
    private String surname;
    private float minSalary;
    private float maxSalary;
    private Date startEmployment;
    private Date endEmployment;
    private Date startLeave;
    private Date endLeave;
}
