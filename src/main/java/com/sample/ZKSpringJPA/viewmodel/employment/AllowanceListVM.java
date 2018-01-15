package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.entity.employment.AllowanceType;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.employment.AllowanceService;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.utils.Frequency;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.utils.Unit;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zsoup.helper.StringUtil;
import org.zkoss.zul.ListModelList;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/allowance-list.zul",
        uuid = "allowance-list",
        menuOrder = "2.6",
        displayName = "Allowance List",
        menuIcon = "money"
)
public class AllowanceListVM extends ListPagingVM {

    //region > Inject Service
    @WireVariable
    private AllowanceService allowanceService;


    //endregion

    //region > Fields
    @Getter
    @Setter
    private ListModelList<Allowance> allowances;

    @Getter @Setter
    private Allowance allowance;

    @Getter
    private final List<AllowanceType> allowanceTypes = new ListModelList<>(AllowanceType.values());

    @Getter
    private final List<Unit> units = new ListModelList<>(Unit.values());

    @Getter
    private final String standardDoubleFormat = StandardFormat.getStandardDoubleFormat();

    @Getter
    private final List<Frequency> frequencyAccruals = new ListModelList<>(Frequency.values());

    @Getter
    private final List<Frequency> frequencyRenewals = new ListModelList<>(Frequency.values());

    //endregion

    //region > Constructor
    @Init
    public void init(){
        allowances = new ListModelList<>(allowanceService.findPaging(0, getPageSize()));
        allowance = new Allowance();
        setTotalSize(allowanceService.count());
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"allowance"})
    public void create(){
        allowance = allowanceService.create(allowance);
        allowances.add(allowance);
    }

    @Command
    @NotifyChange({"allowance"})
    public void update(){
        allowance = allowanceService.update(allowance);
    }

    @Command
    @NotifyChange({"allowance"})
    public void delete(){
        allowanceService.delete(allowance);
        allowances.remove(allowance);
        allowance = new Allowance();
    }

    @Command
    @NotifyChange({"allowance"})
    public void cancel(){
        allowance = new Allowance();
    }

    @Command
    @NotifyChange({"allowance"})
    public void select(@BindingParam("allowance") final Allowance allowance){
        this.allowance = allowance;
    }

    @Override
    public void research(int offset, int limit) {
        if(StringUtil.isBlank(getFilter())) {
            allowances = new ListModelList<>(allowanceService.findPaging(offset, limit));
            setTotalSize(allowanceService.count());
        }
        else {
            allowances = new ListModelList<>(allowanceService.findPaging(offset, limit, getFilter().toLowerCase(), getFilterBy()));
            setTotalSize(allowanceService.count(getFilter().toLowerCase(), getFilterBy()));
        }
        postNotifyChange(this,"allowances");
        postNotifyChange(this,"totalSize");
    }
    //endregion
}
