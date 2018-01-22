package com.sample.ZKSpringJPA.services.request.impl;

import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.request.ApprovalService;
import com.sample.ZKSpringJPA.services.request.dao.ApprovalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("approvalService")
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    ApprovalDao approvalDao;

    @Override
    public Approval find(Long id) {
        return (Approval) approvalDao.find(id, Approval.class);
    }

    @Override
    public Approval create(Approval approval) {
        return (Approval) approvalDao.create(approval);
    }

    @Override
    public Approval update(Approval approval) {
        return (Approval) approvalDao.update(approval);
    }

    @Override
    public void delete(Approval approval) {
        approvalDao.delete(approval);
    }
}
