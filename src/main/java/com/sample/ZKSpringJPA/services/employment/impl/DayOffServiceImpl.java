package com.sample.ZKSpringJPA.services.employment.impl;

import java.util.Date;
import java.util.List;

import com.sample.ZKSpringJPA.entity.employment.DayOff;
import com.sample.ZKSpringJPA.services.employment.DayOffService;
import com.sample.ZKSpringJPA.services.employment.dao.DayOffDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("dayOffService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
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

    @Override
    public int countDayOff(Date from, Date to) {
        return dayOffDao.countDayOff(from, to);
    }

    @Override
    public int count() {
        return dayOffDao.count(DayOff.class);
    }

    @Override
    public List<DayOff> findPaging(int offset, int limit) {
        return dayOffDao.findPaging(offset, limit, DayOff.class);
    }

    @Override
    public int count(String filter, String filterBy) {
        return dayOffDao.count(DayOff.class, filter, filterBy);
    }

    @Override
    public List<DayOff> findPaging(int offset, int limit, String filter, String filterBy) {
        return dayOffDao.findPaging(offset, limit, DayOff.class, filter, filterBy);
    }
}
