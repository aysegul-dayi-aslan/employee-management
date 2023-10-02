package com.aaslan.employeemanagement.model;

import com.aaslan.employeemanagement.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "department")
@Data
@Accessors(chain = true)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @LastModifiedDate
    private Date updated = DateUtils.now();
   
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;
   
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<Employee> employees;
}
