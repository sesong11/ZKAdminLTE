package com.sample.zkspring.services.employment;

import com.sample.zkspring.entity.employment.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department find(Long id);
    Department create(Department department);
    Department update(Department department);
    void delete(Department department);
}
