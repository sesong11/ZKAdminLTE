package com.sample.ZKSpringJPA.services.dashboard;

import com.sample.ZKSpringJPA.entity.dashboard.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> findAll();
    Application find(Long id);
    Application create(Application application);
    Application update(Application application);
    List<Application> queryEnabled();
    void delete(Application application);
}
