package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import com.sample.ZKSpringJPA.services.employment.dao.DepartmentDao;
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

    @Override
    public int count() {
        return departmentDao.count(Department.class);
    }

    @Override
    public List<Department> findPaging(int offset, int limit) {
        return departmentDao.findPaging(offset, limit, Department.class);
    }

    @Override
    public int count(String filter, String filterBy) {
        return departmentDao.count(Department.class, filter, filterBy);
    }

    @Override
    public List<Department> findPaging(int offset, int limit, String filter, String filterBy) {
        return departmentDao.findPaging(offset, limit, Department.class, filter, filterBy);
    }
}
