package com.sample.zkspring.services.employment.impl;

import com.sample.zkspring.entity.employment.Department;
import com.sample.zkspring.services.employment.DepartmentService;
import com.sample.zkspring.services.employment.dao.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentDao departmentDao;
    @Override
    public List<Department> findAll() {
        return departmentDao.queryAll();
    }

    @Override
    public Department find(Long id) {
        return (Department) departmentDao.find(id, Department.class);
    }

    @Override
    public Department create(Department department) {
        return (Department) departmentDao.create(department);
    }

    @Override
    public Department update(Department department) {
        return (Department) departmentDao.update(department);
    }

    @Override
    public void delete(Department department) {
        departmentDao.delete(department);
    }
}
