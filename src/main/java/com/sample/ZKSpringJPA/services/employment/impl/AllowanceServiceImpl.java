package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.employment.AllowanceService;
import com.sample.ZKSpringJPA.services.employment.dao.AllowanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("allowanceService")
public class AllowanceServiceImpl implements AllowanceService {
    @Autowired
    AllowanceDao allowanceDao;

    @Override
    public List<Allowance> findAll() {
        return allowanceDao.queryAll();
    }

    @Override
    public Allowance find(Long id) {
        return (Allowance) allowanceDao.find(id, Allowance.class);
    }

    @Override
    public Allowance create(Allowance allowance) {
        return (Allowance) allowanceDao.create(allowance);
    }

    @Override
    public Allowance update(Allowance allowance) {
        return (Allowance) allowanceDao.update(allowance);
    }

    @Override
    public void delete(Allowance allowance) {
        allowanceDao.delete(allowance);
    }

    @Override
    public int count() {
        return allowanceDao.count(Allowance.class);
    }

    @Override
    public List<Allowance> findPaging(int offset, int limit) {
        return allowanceDao.findPaging(offset, limit, Allowance.class);
    }

    @Override
    public int count(String filter, String filterBy) {
        return allowanceDao.count(Allowance.class, filter, filterBy);
    }

    @Override
    public List<Allowance> findPaging(int offset, int limit, String filter, String filterBy) {
        return allowanceDao.findPaging(offset, limit, Allowance.class, filter, filterBy);
    }
}
