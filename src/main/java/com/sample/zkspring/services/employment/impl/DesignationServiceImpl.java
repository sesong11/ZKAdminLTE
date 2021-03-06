package com.sample.zkspring.services.employment.impl;

import com.sample.zkspring.entity.employment.Designation;
import com.sample.zkspring.services.employment.DesignationService;
import com.sample.zkspring.services.employment.dao.DesignationDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("designationService")
public class DesignationServiceImpl implements DesignationService {
    @Autowired DesignationDao designationDao;
    @Override
    public List<Designation> findAll() {
        return designationDao.queryAll();
    }

    @Override
    public Designation find(Long id) {
        return (Designation) designationDao.find(id, Designation.class);
    }

    @Override
    public Designation create(Designation designation) {
        return (Designation) designationDao.create(designation);
    }

    @Override
    public Designation update(Designation designation) {
        return (Designation) designationDao.update(designation);
    }

    @Override
    public void delete(Designation designation) {
        designationDao.delete(designation);
    }
}
