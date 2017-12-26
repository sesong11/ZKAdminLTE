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
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/awaiting-request.zul",
        uuid = "4-awaiting-approval",
        menuOrder = "3.2",
        displayName = "Awaiting Approve",
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
        requests = new ListModelList<Request>(requestService.findMyRequest(0, pageSize, RequestStatus.OPEN));
        totalSize = requestService.findMyRequestCounter(RequestStatus.OPEN);
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
        requests = new ListModelList<Request>(requestService.findMyRequest(0, pageSize, RequestStatus.OPEN));
    }

    @Command
    @NotifyChange({"requests"})
    public void changeActivePage(@BindingParam("index") final int activePage){
        this.activePage = activePage;
        int offset = activePage* pageSize;
        requests = new ListModelList<Request>(requestService.findMyRequest(offset, pageSize, RequestStatus.OPEN));
    }
    //endregion
}
