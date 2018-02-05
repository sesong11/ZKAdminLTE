package com.sample.zkspring.services.employment;

import java.util.List;

import com.sample.zkspring.entity.employment.DayOff;

public interface DayOffService {
    List<DayOff> findAll();
    DayOff find(Long id);
    DayOff create(DayOff dayOff);
    DayOff update(DayOff dayOff);
    void delete(DayOff dayOff);
}
