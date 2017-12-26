package com.sample.ZKSpringJPA.services.request;

import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.RequestStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;

import java.util.List;
import java.util.TreeSet;

public interface RequestService {
    List<Request> findAll();
    List<Request> findPaging(int offset, int limit);
    List<Request> findMyRequest(int offset, int limit, RequestStatus requestStatus);
    TreeSet<Approval> findApproval(Long id);
    Long count();
    Request find(Long id);
    Request create(Request request);
    Request update(Request request);
    void delete(Request request);

    Long findMyRequestCounter(RequestStatus pending);
}
