package com.sample.ZKSpringJPA.viewmodel.request.components;


import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.RequestStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.request.ApprovalService;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.sql.Timestamp;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AuthorizeVM extends ApproveVM{
    //region > Inject Service
    @WireVariable
    private UserCredentialService userCredentialService;

    @WireVariable
    private ApprovalService approvalService;

    @WireVariable
    private RequestService requestService;
    //endregion

    //region > Fields

    public boolean isApproveAble(){
        if(userCredentialService.getCurrentEmployee()==null || getApproval().getApprovePerson()==null){
            return false;
        }
        boolean approvAble =  userCredentialService.getCurrentEmployee().getId().equals(getApproval().getApprovePerson().getId());
        return (! isApproved()) && approvAble && this.getApproval().getDecisionStatus() == DecisionStatus.AWAITING;
    }

    public boolean isApproved(){
        return !(this.getApproval().getDecisionStatus() == null
                || this.getApproval().getDecisionStatus() == DecisionStatus.AWAITING);
    }

    //endregion

    //region > Constructor

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view,
                     @ExecutionArgParam("componentModel") Approval approval) {
        Selectors.wireComponents(view, this, false);
        this.setApproval(approval);

    }

    //endregion

    //region > Command
    @Command
    @NotifyChange({"approval", "approveAble", "approved"})
    public void approve(){
        getApproval().setDecisionStatus(DecisionStatus.APPROVED);
        getApproval().getRequest().setStatus(RequestStatus.OPEN);
        setApproval(approvalService.update(getApproval()));
        getApproval().setApproveDate(new Timestamp(System.currentTimeMillis()));
        Request request = getApproval().getRequest();
        for (Approval a: request.getApprovals()) {
            if(a.getSortedIndex() == getApproval().getSortedIndex()+1){
                a.setDecisionStatus(DecisionStatus.AWAITING);
                approvalService.update(a);
                break;
            }
        }
        requestService.update(request);
    }
    @Command
    @NotifyChange({"approval", "approveAble", "approved"})
    public void reject(){
        getApproval().setDecisionStatus(DecisionStatus.REJECTED);
        getApproval().getRequest().setStatus(RequestStatus.CLOSED);
        setApproval(approvalService.update(getApproval()));
        getApproval().setApproveDate(new Timestamp(System.currentTimeMillis()));
        requestService.update(getApproval().getRequest());
    }
    //endregion
}
