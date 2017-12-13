package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.services.employment.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAll() {
        return employeeDao.queryAll();
    }

    @Override
    public Employee find(Long id) {
        return (Employee) employeeDao.find(id, Employee.class);
    }

    @Override
    public Employee create(Employee employee) {
        return (Employee) employeeDao.create(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return (Employee) employeeDao.update(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }
}
