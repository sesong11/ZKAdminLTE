package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.EmployeeAllowance;

import java.util.List;

public interface EmployeeAllowanceService {
    List<EmployeeAllowance> findAll();
    EmployeeAllowance find(Long id);
    EmployeeAllowance create(EmployeeAllowance employeeAllowance);
    EmployeeAllowance update(EmployeeAllowance employeeAllowance);
    void delete(EmployeeAllowance employeeAllowance);
}
