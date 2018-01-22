package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;

import java.util.HashMap;
import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee find(Long id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Employee employee);
    Employee findByUser(User user);
    int count();
    List<Employee> findPaging(int offset, int limit);
    int count(final String filter, final String filterBy);
    List<Employee> findPaging(int offset, int limit, String filter, String filterBy);
    int count(final String filter, final String filterBy, HashMap<String, Object> filters);
    List<Employee> findPaging(int offset, int limit, String filter, String filterBy, HashMap<String, Object> filters);
}