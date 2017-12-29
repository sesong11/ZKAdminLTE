package com.sample.ZKSpringJPA.services.dashboard.impl;

import com.sample.ZKSpringJPA.entity.dashboard.Application;
import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.services.dashboard.ApplicationService;
import com.sample.ZKSpringJPA.services.dashboard.dao.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationDao applicationDao;

    @Override
    public List<Application> findAll() {
        return applicationDao.queryAll();
    }

    @Override
    public Application find(Long id) {
        return (Application) applicationDao.find(id, Allowance.class);
    }

    @Override
    public Application create(Application application) {
        return (Application) applicationDao.create(application);
    }

    @Override
    public Application update(Application application) {
        return (Application) applicationDao.update(application);
    }

    @Override
    public void delete(Application application) {
        applicationDao.delete(application);
    }
}
