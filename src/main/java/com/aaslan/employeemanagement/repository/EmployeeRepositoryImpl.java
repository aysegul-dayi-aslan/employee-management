package com.aaslan.employeemanagement.repository;

import com.aaslan.employeemanagement.dto.EmployeeFilter;
import com.aaslan.employeemanagement.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Employee> findAllByFilter(EmployeeFilter filter, Pageable pageable) {
        //Build criteria
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        //Filter operations
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(filter.getName())) {
            predicates.add(builder.equal(employee.get(Employee.Fields.name), filter.getName()));
        }
        if (StringUtils.isNotBlank(filter.getSurname())) {
            predicates.add(builder.equal(employee.get(Employee.Fields.surname), filter.getSurname()));
        }
        if (filter.getMinSalary() > 0) {
            predicates.add(builder.greaterThanOrEqualTo(employee.get(Employee.Fields.salary), filter.getMinSalary()));
        }
        if (filter.getMaxSalary() > 0) {
            predicates.add(builder.lessThan(employee.get(Employee.Fields.salary), filter.getMaxSalary()));
        }
        if (Objects.nonNull(filter.getStartEmployment())) {
            predicates.add(builder.greaterThanOrEqualTo(employee.get(Employee.Fields.employment), filter.getStartEmployment()));
        }
        if (Objects.nonNull(filter.getEndEmployment())) {
            predicates.add(builder.lessThan(employee.get(Employee.Fields.employment), filter.getEndEmployment()));
        }
        if (Objects.nonNull(filter.getStartLeave())) {
            predicates.add(builder.greaterThanOrEqualTo(employee.get(Employee.Fields.leave), filter.getStartLeave()));
        }
        if (Objects.nonNull(filter.getEndLeave())) {
            predicates.add(builder.lessThan(employee.get(Employee.Fields.leave), filter.getEndLeave()));
        }

        //Build query
        query.select(employee)
                .where(builder.and(predicates.toArray(new Predicate[predicates.size()])))
                .orderBy(builder.asc(employee.get(Employee.Fields.name)));

        //Count size of employee by filter
        Long count = (long) entityManager.createQuery(query).getResultList().size();

        return new PageImpl<>(entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList(), pageable, count);
    }
}
