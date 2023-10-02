package com.aaslan.employeemanagement.repository;

import com.aaslan.employeemanagement.model.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Page<Audit> findAllByEmployeeId(Long id, Pageable pageable);
}
