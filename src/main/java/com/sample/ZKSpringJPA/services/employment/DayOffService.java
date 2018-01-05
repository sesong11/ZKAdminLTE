package com.sample.ZKSpringJPA.services.employment;

import java.util.Date;
import java.util.List;

import com.sample.ZKSpringJPA.entity.employment.DayOff;

public interface DayOffService {
    List<DayOff> findAll();
    DayOff find(Long id);
    DayOff create(DayOff dayOff);
    DayOff update(DayOff dayOff);
    void delete(DayOff dayOff);
    int countDayOff(Date from, Date to);
    int count();
    List<DayOff> findPaging(int offset, int limit);
}
