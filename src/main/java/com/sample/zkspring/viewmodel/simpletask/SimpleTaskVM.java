package com.sample.zkspring.viewmodel.simpletask;

import java.util.List;

import com.sample.zkspring.anotation.Feature;
import com.sample.zkspring.services.TaskRunner.SimpleTask;
import com.sample.zkspring.services.TaskRunner.TaskRunnerService;
import com.sample.zkspring.viewmodel.simpletask.builder.ScheduleBuilder;

import org.quartz.SchedulerException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/simple-task/simple-task.zul",
        uuid = "simple-task",
        menuOrder = "4",
        displayName = "Simple Task",
        menuIcon = "task"
)
public class SimpleTaskVM {
    //region > Inject Services

    @WireVariable
    private TaskRunnerService taskRunnerService;
    //endregion

    //region > Fields
    @Getter
    private List<ScheduleType> scheduleTypes = new ListModelList<>(ScheduleType.values());

    @Getter @Setter
    private ScheduleType scheduleType;
    //endregion

    //region > Constructor
    @Init
    public void init() {

    }
    //endregion

    //region > Command
    @Command
    public void changeSchedule(@BindingParam("item") final ScheduleType scheduleType) {
        Window window = (Window) Executions.createComponents(
                scheduleType.getView(), null, null);
        window.doModal();
    }
    @GlobalCommand
    public void runSchedule(@BindingParam("") final ScheduleBuilder scheduleBuilder) throws SchedulerException {
        taskRunnerService.runSimpleTask(SimpleTask.class, scheduleBuilder.build());
    }
    //endregion

}
