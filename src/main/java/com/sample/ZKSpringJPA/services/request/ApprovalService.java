package com.sample.ZKSpringJPA.services.request;

import com.sample.ZKSpringJPA.entity.request.approval.Approval;

public interface ApprovalService {
    Approval find(Long id);
    Approval create(Approval approval);
    Approval update(Approval approval);
    void delete(Approval approval);
}
