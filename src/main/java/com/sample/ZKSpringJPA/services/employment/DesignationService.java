package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Designation;

import java.util.List;

public interface DesignationService {
    List<Designation> findAll();
    Designation find(Long id);
    Designation create(Designation designation);
    Designation update(Designation designation);
    void delete(Designation designation);
    int count();
    List<Designation> findPaging(int offset, int limit);
    int count(final String filter, final String filterBy);
    List<Designation> findPaging(int offset, int limit, final String filter, final String filterBy);
}
