package com.sample.zkspring.services.TaskRunner;

import org.quartz.Job;
import org.quartz.SchedulerException;

public interface TaskRunnerService {
    <T extends Job> void runSimpleTask(Class<T> job, String scronSchedule) throws SchedulerException;
}
