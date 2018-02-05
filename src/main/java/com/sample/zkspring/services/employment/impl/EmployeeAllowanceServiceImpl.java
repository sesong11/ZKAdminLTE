package com.sample.zkspring.services.employment.impl;

import com.sample.zkspring.entity.employment.EmployeeAllowance;
import com.sample.zkspring.services.employment.EmployeeAllowanceService;
import com.sample.zkspring.services.employment.dao.EmployeeAllowanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeAllowanceService")
public class EmployeeAllowanceServiceImpl implements EmployeeAllowanceService {
    @Autowired
    EmployeeAllowanceDao dao;

    @Override
    public List<EmployeeAllowance> findAll() {
        return dao.queryAll();
    }

    @Override
    public EmployeeAllowance find(Long id) {
        return (EmployeeAllowance) dao.find(id, EmployeeAllowance.class);
    }

    @Override
    public EmployeeAllowance create(EmployeeAllowance employeeAllowance) {
        return (EmployeeAllowance) dao.create(employeeAllowance);
    }

    @Override
    public EmployeeAllowance update(EmployeeAllowance employeeAllowance) {
        return (EmployeeAllowance) dao.update(employeeAllowance);
    }

    @Override
    public void delete(EmployeeAllowance employeeAllowance) {
        dao.delete(employeeAllowance);
    }
}
