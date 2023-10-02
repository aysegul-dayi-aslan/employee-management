package com.aaslan.employeemanagement.service;

import com.aaslan.employeemanagement.model.Audit;
import com.aaslan.employeemanagement.model.Department;
import com.aaslan.employeemanagement.model.Employee;
import com.aaslan.employeemanagement.repository.AuditRepository;
import com.aaslan.employeemanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditRepository auditRepository;

    public void auditRecord(Employee employee, Department department) {
        Date start = DateUtils.now();
        Audit audit = new Audit();
        audit.setEmployeeId(employee.getId())
                .setOldDeparment(employee.getDepartment().getName())
                .setNewDepartment(department.getName());
        auditRepository.save(audit);
        log.info("Audit record inserted for employee({})", employee.getId());
        log.info("Audit record persist query has been completed in {} milliseconds", DateUtils.difference(start));
    }

    public ResponseEntity<Page<Audit>> audits(Long id, int page, int size) {
        try {
            Date start = DateUtils.now();
            Page<Audit> audits = auditRepository.findAllByEmployeeId(id, PageRequest.of(page, size, Sort.Direction.ASC, "updated"));
            log.info("Audit records list query has been completed in {} milliseconds", DateUtils.difference(start));
            return new ResponseEntity<>(audits, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unexpected error while reading audits. {} : {}", e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
