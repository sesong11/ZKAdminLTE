package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.RequestStatus;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/awaiting-request.zul",
        uuid = "4-awaiting-approval",
        menuOrder = "3.2",
        displayName = "Awaiting Approval",
        menuIcon = "spinner"
)
public class AwaitingApprovalVM {
    //region > Inject Service
    @WireVariable
    private RequestService requestService;
    //endregion

    //region > Fields
    @Getter
    @Setter
    private List<Request> requests;

    @Getter @Setter
    private int pageSize = 10;

    @Getter
    private Long totalSize;

    @Getter
    private int activePage;

    @Getter
    private String standardDateTimeFormat = StandardFormat.getStandardDateTimeFormat();
    //endregion

    //region > Constructor
    @Init
    public void init() {
        requests = new ListModelList<Request>(requestService.findMyRequestAwaiting(0, pageSize));
        totalSize = requestService.findMyRequestAwaitingCounter();
    }
    //endregion

    //region > Command
    @Command
    public void open(@BindingParam("item") final Request request){

    }

    @Command
    @NotifyChange({"pageSize", "requests"})
    public void changePageSize(@BindingParam("size") final int pageSize){
        this.pageSize = pageSize;
        requests = new ListModelList<Request>(requestService.findMyRequestAwaiting(0, pageSize));
    }

    @Command
    @NotifyChange({"requests"})
    public void changeActivePage(@BindingParam("index") final int activePage){
        this.activePage = activePage;
        int offset = activePage* pageSize;
        requests = new ListModelList<Request>(requestService.findMyRequestAwaiting(offset, pageSize));
    }
    @Command
    public void openForm(@BindingParam("request") final Request request) throws ClassNotFoundException {
        Executions.sendRedirect("?m="+request.getFormType().getUuid()+"&id="+request.getId());
    }
    //endregion
}
