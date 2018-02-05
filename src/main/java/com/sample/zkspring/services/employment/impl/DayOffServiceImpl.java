package com.sample.zkspring.services.employment.impl;

import java.util.List;

import com.sample.zkspring.entity.employment.DayOff;
import com.sample.zkspring.services.employment.DayOffService;
import com.sample.zkspring.services.employment.dao.DayOffDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dayOffService")
public class DayOffServiceImpl implements DayOffService {
    @Autowired
    private DayOffDao dayOffDao;

    @Override public List<DayOff> findAll() {
        return dayOffDao.queryAll();
    }

    @Override public DayOff find(final Long id) {
        return (DayOff) dayOffDao.find(id, DayOff.class);
    }

    @Override public DayOff create(final DayOff dayOff) {
        return (DayOff) dayOffDao.create(dayOff);
    }

    @Override public DayOff update(final DayOff dayOff) {
        return (DayOff) dayOffDao.update(dayOff);
    }

    @Override public void delete(final DayOff dayOff) {
        dayOffDao.delete(dayOff);
    }
}
