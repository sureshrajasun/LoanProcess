package com.example.workflow;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Deployment(resources = "LoanProcessByType.bpmn")
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoanProcessBPMNTest extends AbstractProcessEngineRuleTest {

    @Test
    public void loanProcessingStartAndFinish() throws InterruptedException {

        final ProcessInstance processInstance = runtimeService().createMessageCorrelation("Msg-StartLoanProcess")
                .setVariable("name", "David Smith")
                .setVariable("age", 40)
                .setVariable("loanType", "PL")
                .setVariable("loanAmount", 3200)
                .correlateStartMessage();

        assertThat(processInstance).isWaitingAt("BasicCheckActivity");

        complete(task());

        assertThat(processInstance).isWaitingAt("personalLoanActivity");

        complete(task());

        assertThat(processInstance).isWaitingAt("dispatchActivity");

        assertEquals(externalTaskService().getTopicNames().get(0), "loanDispatch");

        externalTaskService().fetchAndLock(10, "externalWorkerId", true)
                .topic("loanDispatch", 5000L)
                .execute();

        externalTaskService().complete(externalTask().getId(),"externalWorkerId", new HashMap<>());

        assertThat(processInstance).isEnded();
    }
}
