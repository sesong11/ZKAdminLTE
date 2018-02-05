package com.sample.zkspring.services.employment;

import com.sample.zkspring.entity.employment.EmploymentHistory;


public interface EmployeeHistoryService {
    EmploymentHistory find(Long id);
    EmploymentHistory create(EmploymentHistory employeeHistory);
    EmploymentHistory update(EmploymentHistory employeeHistory);
    void delete(EmploymentHistory employeeHistory);
}
