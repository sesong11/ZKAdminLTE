package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.EmploymentHistory;


public interface EmployeeHistoryService {
    EmploymentHistory find(Long id);
    EmploymentHistory create(EmploymentHistory employeeHistory);
    EmploymentHistory update(EmploymentHistory employeeHistory);
    void delete(EmploymentHistory employeeHistory);
}
