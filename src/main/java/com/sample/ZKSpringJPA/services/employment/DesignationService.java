package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.entity.employment.Designation;
import com.sample.ZKSpringJPA.entity.employment.Employee;

import java.util.List;

public interface DesignationService {
    List<Designation> findAll();
    Designation find(Long id);
    Designation create(Designation designation);
    Designation update(Designation designation);
    void delete(Designation designation);
    int count();
    List<Designation> findPaging(int offset, int limit);
}
