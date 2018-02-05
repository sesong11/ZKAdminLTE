package com.sample.zkspring.services.TaskRunner;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleTask implements Job {

    @Override public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Task Start"+ new Date());
    }
}
