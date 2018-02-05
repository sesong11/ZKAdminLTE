package com.sample.zkspring.services.employment;

import com.sample.zkspring.entity.employment.Allowance;

import java.util.List;

public interface AllowanceService {
    List<Allowance> findAll();
    Allowance find(Long id);
    Allowance create(Allowance allowance);
    Allowance update(Allowance allowance);
    void delete(Allowance allowance);
}
