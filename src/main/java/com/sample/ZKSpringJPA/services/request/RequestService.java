package com.sample.ZKSpringJPA.services.request;

import com.sample.ZKSpringJPA.entity.request.Request;

import java.util.List;

public interface RequestService {
    List<Request> findAll();
    List<Request> findPaging(int offset, int limit);
    Long count();
    Request find(Long id);
    Request create(Request request);
    Request update(Request request);
    void delete(Request request);
}
