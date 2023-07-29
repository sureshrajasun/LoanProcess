package com.example.workflow;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


public class LoanProcessTest {

    @RegisterExtension
    ProcessEngineExtension extension = ProcessEngineExtension.builder()
            .build();

    @Test
   // @Deployment(resources = {"LoanProcessByType.bpmn"})
    public void shouldExecuteProcess() {
        RuntimeService runtimeService = extension.getProcessEngine().getRuntimeService();
        // Given we create a new process instance
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Loan_Approval_Process_by_Type");
        // Then it should be active

        // And it should be the only instance
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        // And there should exist just a single task within that process instance
        assertThat(task(processInstance)).isNotNull();

        // When we complete that task
        complete(task(processInstance));
        // Then the process instance should be ended

    }

}