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
        view = "/view/employment/allowancelist.zul",
        uuid = "allowancelist",
        menuOrder = "2.6",
        displayName = "Allowance List",
        menuIcon = "money"
)
public class AllowanceListVM {

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
        allowances = new ListModelList<>(allowanceService.findAll());
        allowance = new Allowance();
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
    //endregion
}
