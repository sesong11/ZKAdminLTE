package com.sample.ZKSpringJPA.entity.request.approval;

import lombok.Getter;
import lombok.Setter;

public enum ApprovalType {
    RELIEF(1, "Relief", "/view/request/component/leave-relief.zul"),
    APPROVE(2, "Approve", "/view/request/component/request-approve.zul"),
    AUTHORIZE(3, "Authorize", "/view/request/component/request-authorize.zul");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String view;


    ApprovalType(final int value, final String name, final String view) {
        this.value = value;
        this.name = name;
        this.view = view;
    }
}
