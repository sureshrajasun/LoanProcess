package com.example.workflow.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Slf4j
@Named(value="EmailNotification")
public class EmailNotification implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
            log.info("Email to '{}' is sent successfully with the loan of type '{}' of Amount : {}.",
                    delegateExecution.getVariable("name"), delegateExecution.getVariable("loanType"), delegateExecution.getVariable("loanAmount") );
    }
}