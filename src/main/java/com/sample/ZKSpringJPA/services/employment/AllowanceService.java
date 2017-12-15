package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Allowance;

import java.util.List;

public interface AllowanceService {
    List<Allowance> findAll();
    Allowance find(Long id);
    Allowance create(Allowance allowance);
    Allowance update(Allowance allowance);
    void delete(Allowance allowance);
}
