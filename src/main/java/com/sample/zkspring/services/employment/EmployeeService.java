package com.sample.zkspring.services.employment;

import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.entity.employment.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee find(Long id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Employee employee);
    Employee findByUser(User user);
}
