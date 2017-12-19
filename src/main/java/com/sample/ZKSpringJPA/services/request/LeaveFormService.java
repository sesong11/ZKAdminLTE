package com.sample.ZKSpringJPA.services.request;

import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;

import java.util.List;

public interface LeaveFormService {
    LeaveForm find(Long id);
    LeaveForm create(LeaveForm leaveForm);
    LeaveForm update(LeaveForm leaveForm);
    void delete(LeaveForm leaveForm);
}
