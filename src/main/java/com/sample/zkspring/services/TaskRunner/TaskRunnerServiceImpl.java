package com.sample.zkspring.services.TaskRunner;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

@Service("taskRunnerService")
public class TaskRunnerServiceImpl implements TaskRunnerService {

    @Override public <T extends Job> void runSimpleTask(Class<T> job, final String scronSchedule)
            throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(job).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getName()).withSchedule(CronScheduleBuilder.cronSchedule(scronSchedule)).build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
