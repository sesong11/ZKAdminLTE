package com.sample.zkspring.services.employment;

import com.sample.zkspring.entity.employment.Designation;

import java.util.List;

public interface DesignationService {
    List<Designation> findAll();
    Designation find(Long id);
    Designation create(Designation designation);
    Designation update(Designation designation);
    void delete(Designation designation);
}
