package com.sample.ZKSpringJPA.services.employment.impl;

import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.dao.BranchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("branchService")
public class BranchServiceImpl implements BranchService {
    @Autowired
    BranchDao branchDao;

    @Override
    public List<Branch> findAll() {
        return branchDao.queryAll();
    }

    @Override
    public Branch find(final Long id) {
        return (Branch)branchDao.find(id, Branch.class);
    }

    @Override
    public Branch create(final Branch branch) {
        return (Branch) branchDao.create(branch);
    }

    @Override
    public Branch update(Branch branch) {
        return (Branch) branchDao.update(branch);
    }

    @Override
    public void delete(Branch branch) {
        branchDao.delete(branch);
    }

    @Override
    public int count() {
        return branchDao.count(Branch.class);
    }

    @Override
    public List<Branch> findPaging(int offset, int limit) {
        return branchDao.findPaging(offset, limit, Branch.class);
    }
}
