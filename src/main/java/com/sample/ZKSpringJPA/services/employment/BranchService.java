package com.sample.ZKSpringJPA.services.employment;

import com.sample.ZKSpringJPA.entity.employment.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> findAll();
    Branch find(Long id);
    Branch create(Branch branch);
    Branch update(Branch branch);
    void delete(Branch branch);
    int count();
    List<Branch> findPaging(int offset, int limit);
    int count(final String filter, final String filterBy);
    List<Branch> findPaging(int offset, int limit, final String filter, final String filterBy);
}
