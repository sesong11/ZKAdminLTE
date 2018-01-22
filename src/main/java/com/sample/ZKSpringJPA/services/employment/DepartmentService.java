package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department find(Long id);
    Department create(Department department);
    Department update(Department department);
    void delete(Department department);
    int count();
    List<Department> findPaging(int offset, int limit);
    int count(final String filter, final String filterBy);
    List<Department> findPaging(int offset, int limit, final String filter, final String filterBy);
}
