package com.sample.ZKSpringJPA.services.request.impl;

import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;
import com.sample.ZKSpringJPA.services.request.LeaveFormService;
import com.sample.ZKSpringJPA.services.request.dao.LeaveFormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("leaveFormService")
public class LeaveFormServiceImpl implements LeaveFormService {

    @Autowired
    LeaveFormDao leaveFormDao;

    @Override
    public LeaveForm find(Long id) {
        return (LeaveForm) leaveFormDao.find(id, LeaveForm.class);
    }

    @Override
    public LeaveForm findByRequestId(Long id) {
        return (LeaveForm) leaveFormDao.findByRequestId(id);
    }

    @Override
    public LeaveForm create(LeaveForm leaveForm) {
        return (LeaveForm) leaveFormDao.create(leaveForm);
    }

    @Override
    public LeaveForm update(LeaveForm leaveForm) {
        return (LeaveForm) leaveFormDao.update(leaveForm);
    }

    @Override
    public void delete(LeaveForm leaveForm) {
        leaveFormDao.delete(leaveForm);
    }
}
