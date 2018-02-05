package com.sample.zkspring.services.employment.impl;

import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.entity.employment.Employee;
import com.sample.zkspring.services.employment.EmployeeService;
import com.sample.zkspring.services.employment.dao.EmployeeDao;
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

    @Override
    public Employee findByUser(User user){
        return employeeDao.findByUser(user);
    }
}
