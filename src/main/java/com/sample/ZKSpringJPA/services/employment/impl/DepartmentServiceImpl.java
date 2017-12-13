package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Department find(Long id) {
        return null;
    }

    @Override
    public Department create(Department department) {
        return null;
    }

    @Override
    public Department update(Department department) {
        return null;
    }

    @Override
    public void delete(Department department) {

    }
}
