package com.sample.ZKSpringJPA.viewmodel.request.components;

import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import lombok.Getter;
import lombok.Setter;

public abstract class ApprovalVM {
    @Getter @Setter
    private Approval approval;

    public abstract boolean isApproved();

    public String getBoxClass(){
        String str = "";
        if(!isApproved())
            str = "info";
        else if(approval.getDecisionStatus() == DecisionStatus.REJECTED)
            str = "danger";
        else
            str = "success";
        return str;
    }

    public String getBadgeColor(){
        String str = "";
        if(!isApproved())
            str = "default";
        else if(approval.getDecisionStatus() == DecisionStatus.REJECTED)
            str = "red";
        else
            str = "green";
        return str;
    }
}
