package com.example.workflow.service;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.spring.boot.starter.ClientProperties;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ExternalTaskSubscription("loanDispatch")
public class DispatchService implements ExternalTaskHandler {

    protected static Logger LOG = LoggerFactory.getLogger(DispatchService.class);
    protected String workerId;
    public DispatchService(ClientProperties properties) {
        workerId = properties.getWorkerId();
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        externalTaskService.complete(externalTask);

        LOG.info("{}: The loan process {} has been approved!", workerId, externalTask.getId());
    }
}
