package com.sample.ZKSpringJPA.services.request.impl;

import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.services.request.dao.RequestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Service("requestService")
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestDao requestDao;

    @Override
    public List<Request> findAll() {
        return requestDao.queryAll();
    }

    @Override
    public List<Request> findPaging(final int offset, final int limit) {
        return requestDao.findPaging(offset, limit);
    }

    @Override
    public TreeSet<Approval> findApproval(Long id) {
        return requestDao.findApproval(id);
    }

    @Override
    public Long count(){
        return requestDao.count();
    }

    @Override
    public Request find(Long id) {
        return (Request) requestDao.find(id, Request.class);
    }

    @Override
    public Request create(Request request) {
        return (Request) requestDao.create(request);
    }

    @Override
    public Request update(Request request) {
        return (Request) requestDao.update(request);
    }

    @Override
    public void delete(Request request) {
        requestDao.delete(request);
    }
}
