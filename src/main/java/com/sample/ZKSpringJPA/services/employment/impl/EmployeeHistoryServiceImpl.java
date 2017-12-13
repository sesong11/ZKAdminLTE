package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.EmploymentHistory;
import com.sample.ZKSpringJPA.services.employment.EmployeeHistoryService;
import org.springframework.stereotype.Service;

@Service("employeeHistoryService")
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {
    @Override
    public EmploymentHistory find(Long id) {
        return null;
    }

    @Override
    public EmploymentHistory create(EmploymentHistory employeeHistory) {
        return null;
    }

    @Override
    public EmploymentHistory update(EmploymentHistory employeeHistory) {
        return null;
    }

    @Override
    public void delete(EmploymentHistory employeeHistory) {

    }
}
