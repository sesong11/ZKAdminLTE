package com.sample.ZKSpringJPA.viewmodel.request.components;


import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
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

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReliefVM {

    //region > Inject Service
    @WireVariable
    private UserCredentialService userCredentialService;

    @WireVariable
    private ApprovalService approvalService;

    @WireVariable
    private RequestService requestService;
    //endregion

    //region > Fields
    @Getter @Setter
    private Approval approval;

    public boolean isApproveAble(){
        if(userCredentialService.getCurrentEmployee()==null || approval.getApprovePerson()==null){
            return false;
        }
        boolean approvAble =  userCredentialService.getCurrentEmployee().getId().equals(approval.getApprovePerson().getId());
        System.out.println(userCredentialService.getCurrentEmployee().getId() +" : "+approvAble+":"+ approval.getApprovePerson().getId());
        return (! isApproved()) && approvAble;
    }

    public boolean isApproved(){
        return this.approval.getDecisionStatus() != DecisionStatus.AWAITING;
    }

    //endregion

    //region > Constructor

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view,
                     @ExecutionArgParam("componentModel") Approval approval) {
        Selectors.wireComponents(view, this, false);
        this.approval = approval;

    }

    //endregion

    //region > Command
    @Command
    @NotifyChange({"approval", "approveAble", "approved"})
    public void approve(){
        approval.setDecisionStatus(DecisionStatus.APPROVED);
        approval.getRequest().setStatus(RequestStatus.OPEN);
        approval = approvalService.update(approval);
        requestService.update(approval.getRequest());
    }
    //endregion

}
