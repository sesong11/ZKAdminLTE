package com.sample.ZKSpringJPA.services.employment;

import java.util.List;

import com.sample.ZKSpringJPA.entity.employment.DayOff;

public interface DayOffService {
    List<DayOff> findAll();
    DayOff find(Long id);
    DayOff create(DayOff dayOff);
    DayOff update(DayOff dayOff);
    void delete(DayOff dayOff);
}
