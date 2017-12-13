package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Designation;
import com.sample.ZKSpringJPA.entity.employment.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee find(Long id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Employee employee);
}
