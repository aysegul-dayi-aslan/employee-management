package com.aaslan.employeemanagement.dto;

import com.aaslan.employeemanagement.model.Location;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DepartmentDto {
    private Long id;
    private String name;
    private Location location;
}
