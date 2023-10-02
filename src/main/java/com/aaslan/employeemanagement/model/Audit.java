package com.aaslan.employeemanagement.model;

import com.aaslan.employeemanagement.utils.DateUtils;
import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "audit")
@Data
@Accessors(chain = true)
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @LastModifiedDate
    private Date updated = DateUtils.now();

    @Column(name = "old_department")
    private String oldDeparment;

    @Column(name = "new_department")
    private String newDepartment;
}
